package pt.sensae.services.device.commander.backend.domain.model;

import pt.sensae.services.device.commander.backend.domain.model.records.DeviceInformation;
import pt.sensae.services.device.commander.backend.domain.model.device.DeviceId;

import java.util.Optional;

public interface DeviceRepository {

    Optional<DeviceInformation> findByDeviceId(DeviceId id);

    DeviceInformation add(DeviceInformation domain);
}
