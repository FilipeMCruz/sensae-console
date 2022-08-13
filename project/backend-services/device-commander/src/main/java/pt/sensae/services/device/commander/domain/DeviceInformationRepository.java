package pt.sensae.services.device.commander.domain;

import pt.sensae.services.device.commander.domain.device.DeviceId;

import java.util.Optional;

public interface DeviceInformationRepository {

    Optional<DeviceInformation> findById(DeviceId id);

    void update(DeviceInformation info);

    void delete(DeviceId id);
}
