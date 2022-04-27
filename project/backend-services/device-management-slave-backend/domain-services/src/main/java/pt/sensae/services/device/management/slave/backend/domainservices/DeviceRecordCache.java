package pt.sensae.services.device.management.slave.backend.domainservices;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.slave.backend.domain.model.DeviceRepository;
import pt.sensae.services.device.management.slave.backend.domain.model.commands.DeviceCommands;
import pt.sensae.services.device.management.slave.backend.domain.model.device.Device;
import pt.sensae.services.device.management.slave.backend.domain.model.device.DeviceId;
import pt.sensae.services.device.management.slave.backend.domain.model.device.DeviceName;
import pt.sensae.services.device.management.slave.backend.domain.model.records.DeviceInformation;
import pt.sensae.services.device.management.slave.backend.domain.model.records.Records;
import pt.sensae.services.device.management.slave.backend.domain.model.subDevices.SubDevices;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;

import java.time.Duration;
import java.util.Objects;

@Service
public class DeviceRecordCache {

    private final Cache<DeviceId, DeviceInformation> cache;

    private final DeviceRepository repository;

    public DeviceRecordCache(DeviceRepository repository) {
        this.repository = repository;
        this.cache = Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofHours(12))
                .maximumSize(50)
                .build();
    }

    public DeviceInformation findByDeviceId(DeviceId id, DeviceName name) {
        return Objects.requireNonNullElseGet(cache.getIfPresent(id), () -> update(id, name));
    }

    public void update(DeviceId id) {
        var deviceById = repository.findByDeviceId(id);
        if (deviceById.isPresent()) {
            cache.put(id, deviceById.get());
        } else {
            cache.invalidate(id);
        }
    }

    public DeviceInformation update(DeviceId id, DeviceName name) {
        var deviceById = repository.findByDeviceId(id);
        var device = deviceById.isEmpty() ? this.create(id, name) : deviceById.get();
        cache.put(id, device);
        return device;
    }

    private DeviceInformation create(DeviceId id, DeviceName name) {
        return repository.add(new DeviceInformation(new Device(id, name), Records.empty(), SubDevices.empty(), DeviceCommands.empty()));
    }
}
