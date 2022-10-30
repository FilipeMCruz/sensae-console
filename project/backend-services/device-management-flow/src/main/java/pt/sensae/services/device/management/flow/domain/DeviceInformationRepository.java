package pt.sensae.services.device.management.flow.domain;

import pt.sensae.services.device.management.flow.domain.device.DeviceId;

import java.util.Optional;

public interface DeviceInformationRepository {

    Optional<DeviceInformation> findById(DeviceId id);

    void update(DeviceInformation info);

    void delete(DeviceId id);
}
