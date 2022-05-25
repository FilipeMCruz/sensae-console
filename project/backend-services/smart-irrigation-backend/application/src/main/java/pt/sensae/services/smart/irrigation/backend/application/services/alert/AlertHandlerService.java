package pt.sensae.services.smart.irrigation.backend.application.services.alert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.application.services.command.DeviceCommandPublisher;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.*;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.command.DeviceCommand;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.LedgerEntry;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.Ownership;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.payload.ValveStatusType;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.GardeningAreaCache;
import pt.sharespot.iot.core.alert.model.AlertDTO;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AlertHandlerService {

    Logger logger = LoggerFactory.getLogger(AlertHandlerService.class);

    private final DeviceRepository deviceRepository;

    private final DeviceCommandPublisher publisher;

    private final GardeningAreaCache gardenCache;

    public AlertHandlerService(DeviceRepository deviceRepository, DeviceCommandPublisher publisher, GardeningAreaCache gardenCache) {
        this.deviceRepository = deviceRepository;
        this.publisher = publisher;
        this.gardenCache = gardenCache;
    }

    public void handle(AlertDTO data, ValveStatusType toOpenValve) {
        var deviceIds = data.context.deviceIds.stream()
                .map(DeviceId::new).collect(Collectors.toSet());

        if (deviceIds.isEmpty()) {
            logger.info("No correlation provided");
            return;
        }

        var allDevices = deviceRepository.fetchLatest(Ownership.system()).collect(Collectors.toSet());

        var devicesLatestData = allDevices.stream().filter(dev -> deviceIds.contains(dev.id()))
                .collect(Collectors.toSet());

        if (devicesLatestData.size() != deviceIds.size()) {
            logger.info("Some Devices are invalids");
            return;
        }

        var deviceCoordinates = devicesLatestData.stream()
                .map(device -> device.ledger().entries().stream().findFirst())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(entry -> entry.content().coordinates())
                .collect(Collectors.toSet());

        var gardeningAreasInvolved = gardenCache.fetchAll(Ownership.system())
                .filter(area -> deviceCoordinates.stream().anyMatch(cord -> area.area().contains(cord))).toList();

        if (gardeningAreasInvolved.size() != 1) {
            logger.info("Invalid correlation provided: devices don't mentioned refer a single gardening area");
            return;
        }

        var gardeningArea = gardeningAreasInvolved.get(0);


        Set<Device> valvesToTrigger = new HashSet<>();

        for (Device device : allDevices) {
            if (device.type().equals(DeviceType.VALVE)) {
                var entry = device.ledger().entries().stream().findFirst();
                if (entry.isPresent() &&
                        entry.get().content().remoteControl().value() &&
                        gardeningArea.area().contains(entry.get().content().coordinates())) {
                    valvesToTrigger.add(device);
                }
            }
        }
        valvesToTrigger.forEach(valve -> {
            publisher.publish(new DeviceCommand(ValveCommand.from(toOpenValve), valve.id()));
        });
    }
}
