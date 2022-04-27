package pt.sensae.services.device.commander.backend.domainservices;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import pt.sensae.services.device.commander.backend.domain.model.DeviceRepository;
import pt.sensae.services.device.commander.backend.domain.model.commands.DeviceCommands;
import pt.sensae.services.device.commander.backend.domain.model.device.Device;
import pt.sensae.services.device.commander.backend.domain.model.device.DeviceId;
import pt.sensae.services.device.commander.backend.domain.model.records.DeviceInformation;
import pt.sensae.services.device.commander.backend.domain.model.records.Records;
import pt.sensae.services.device.commander.backend.domain.model.subDevices.SubDevices;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

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

    public Optional<DeviceInformation> findByDeviceId(DeviceId deviceId) {
        var ifPresent = cache.getIfPresent(deviceId);
        return ifPresent == null ? update(deviceId) : Optional.of(ifPresent);
    }

    public Optional<DeviceInformation> update(DeviceId id) {
        var deviceById = repository.findByDeviceId(id);
        if (deviceById.isPresent()) {
            cache.put(id, deviceById.get());
        } else {
            cache.invalidate(id);
        }
        return deviceById;
    }
}
