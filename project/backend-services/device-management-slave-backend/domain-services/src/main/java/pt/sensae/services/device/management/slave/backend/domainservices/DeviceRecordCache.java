package pt.sensae.services.device.management.slave.backend.domainservices;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.slave.backend.domain.model.DeviceRepository;
import pt.sensae.services.device.management.slave.backend.domain.model.commands.DeviceCommands;
import pt.sensae.services.device.management.slave.backend.domain.model.device.Device;
import pt.sensae.services.device.management.slave.backend.domain.model.device.DeviceDownlink;
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

    public DeviceInformation findByDeviceId(Device device) {
        var deviceInCache = Objects.requireNonNullElseGet(cache.getIfPresent(device.id()), () -> update(device));
        if (!device.downlink().equals(deviceInCache.device().downlink())) {
            this.repository.updateDownlink(device);
        }
        return deviceInCache;
    }

    public void update(DeviceId id) {
        var deviceById = repository.findByDeviceId(id);
        if (deviceById.isPresent()) {
            cache.put(id, deviceById.get());
        } else {
            cache.invalidate(id);
        }
    }

    public DeviceInformation update(Device device) {
        var deviceById = repository.findByDeviceId(device.id());
        var information = deviceById.isEmpty() ? this.create(device) : deviceById.get();
        cache.put(device.id(), information);
        return information;
    }

    private DeviceInformation create(Device device) {
        return repository.add(new DeviceInformation(device, Records.empty(), SubDevices.empty(), DeviceCommands.empty()));
    }
}
