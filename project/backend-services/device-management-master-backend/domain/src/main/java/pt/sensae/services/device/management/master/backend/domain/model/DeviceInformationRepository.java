package pt.sensae.services.device.management.master.backend.domain.model;

import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceId;

import java.util.Optional;
import java.util.stream.Stream;

public interface DeviceInformationRepository {

    DeviceInformation save(DeviceInformation records);

    Optional<DeviceInformation> findById(DeviceId id);

    Stream<DeviceInformation> findAll();

    Stream<DeviceInformation> findAllById(Stream<DeviceId> deviceIds);

    DeviceId delete(DeviceId id);
}
