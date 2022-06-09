package pt.sensae.services.device.management.master.backend.domainservices;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.master.backend.domain.model.DeviceInformation;
import pt.sensae.services.device.management.master.backend.domain.model.DeviceInformationRepository;
import pt.sensae.services.device.management.master.backend.domain.model.commands.DeviceCommands;
import pt.sensae.services.device.management.master.backend.domain.model.device.Device;
import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceId;
import pt.sensae.services.device.management.master.backend.domain.model.records.DeviceRecords;
import pt.sensae.services.device.management.master.backend.domain.model.staticData.DeviceStaticData;
import pt.sensae.services.device.management.master.backend.domain.model.subDevices.SubDevices;

import java.util.stream.Stream;

@Service
public class DeviceInformationCollector {

    private final DeviceInformationRepository repository;

    public DeviceInformationCollector(DeviceInformationRepository repository) {
        this.repository = repository;
    }

    public Stream<DeviceInformation> collect() {
        return repository.findAll();
    }

    public Stream<DeviceInformation> collect(Stream<DeviceId> deviceIdsStream) {
        return repository.findAllById(deviceIdsStream);
    }

    public DeviceInformation collect(Device device) {
        var info = repository.findById(device.id());
        if (info.isEmpty()) {
            return repository.save(new DeviceInformation(device, DeviceRecords.empty(), DeviceStaticData.empty(), SubDevices.empty(), DeviceCommands.empty()));
        } else {
            var dev = info.get();
            return !device.downlink().value().isBlank() && !dev.device()
                    .downlink()
                    .equals(device.downlink()) ? repository.save(dev.withDownlink(device.downlink())) : dev;
        }
    }
}
