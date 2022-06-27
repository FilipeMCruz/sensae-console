package pt.sensae.services.device.management.master.backend.domain.model;

import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceId;

import java.time.Instant;
import java.util.Optional;

public interface LastTimeSeenDeviceRepository {

    void update(DeviceId id);

    Optional<Instant> find(DeviceId id);

}
