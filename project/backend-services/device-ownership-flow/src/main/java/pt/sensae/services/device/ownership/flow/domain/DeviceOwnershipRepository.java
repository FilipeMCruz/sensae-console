package pt.sensae.services.device.ownership.flow.domain;


import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface DeviceOwnershipRepository {

    Optional<DeviceWithAllPermissions> findById(DeviceId id);

    void update(DeviceWithAllPermissions info);

    void delete(DeviceId id);

    Optional<List<DeviceWithAllPermissions>> findAllById(Stream<DeviceId> devices);
}
