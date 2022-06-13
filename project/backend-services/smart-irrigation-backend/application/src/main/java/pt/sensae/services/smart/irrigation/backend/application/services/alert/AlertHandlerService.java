package pt.sensae.services.smart.irrigation.backend.application.services.alert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.application.services.command.DeviceCommandPublisher;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.*;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.command.DeviceCommand;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.Ownership;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.DataRepository;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.payload.ValvePayload;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.payload.ValveStatusType;
import pt.sensae.services.smart.irrigation.backend.domainservices.irrigationZone.IrrigationZoneCache;
import pt.sharespot.iot.core.alert.model.AlertDTO;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AlertHandlerService {

    private final Logger logger = LoggerFactory.getLogger(AlertHandlerService.class);

    private final DeviceRepository deviceRepository;

    private final DataRepository dataRepository;

    private final DeviceCommandPublisher publisher;

    private final IrrigationZoneCache irrigationZoneCache;

    public AlertHandlerService(DeviceRepository deviceRepository,
                               DataRepository dataRepository,
                               DeviceCommandPublisher publisher,
                               IrrigationZoneCache irrigationZoneCache) {
        this.deviceRepository = deviceRepository;
        this.dataRepository = dataRepository;
        this.publisher = publisher;
        this.irrigationZoneCache = irrigationZoneCache;
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

        var irrigationZonesInvolved = irrigationZoneCache.fetchAll(Ownership.system())
                .filter(area -> deviceCoordinates.stream().anyMatch(cord -> area.area().contains(cord))).toList();

        if (irrigationZonesInvolved.size() != 1) {
            logger.info("Invalid correlation provided: devices don't mentioned refer a single irrigation zone");
            return;
        }

        var irrigationZone = irrigationZonesInvolved.get(0);


        Set<Device> valvesToTrigger = new HashSet<>();

        for (Device device : allDevices) {
            if (device.type().equals(DeviceType.VALVE)) {
                var entry = device.ledger().entries().stream().findFirst();
                if (entry.isPresent() &&
                        entry.get().content().remoteControl().value() &&
                        irrigationZone.area().contains(entry.get().content().coordinates())) {
                    valvesToTrigger.add(device);
                }
            }
        }
        dataRepository.fetchLatest(valvesToTrigger.stream().map(Device::id))
                .filter(reading -> reading.payload() instanceof ValvePayload)
                .filter(valvePayload -> ((ValvePayload) valvePayload.payload()).status().value() != toOpenValve)
                .forEach(valve -> publisher.publish(new DeviceCommand(ValveCommand.from(toOpenValve), valve.deviceId())));
    }
}
