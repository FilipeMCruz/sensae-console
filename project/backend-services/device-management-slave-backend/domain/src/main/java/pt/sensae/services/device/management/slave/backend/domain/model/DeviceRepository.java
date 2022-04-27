package pt.sensae.services.device.management.slave.backend.domain.model;

import pt.sensae.services.device.management.slave.backend.domain.model.device.DeviceId;
import pt.sensae.services.device.management.slave.backend.domain.model.records.DeviceInformation;

import java.util.Optional;

public interface DeviceRepository {

    Optional<DeviceInformation> findByDeviceId(DeviceId id);

    DeviceInformation add(DeviceInformation domain);
}
