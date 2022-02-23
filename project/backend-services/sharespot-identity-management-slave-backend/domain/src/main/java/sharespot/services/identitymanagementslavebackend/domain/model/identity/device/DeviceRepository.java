package sharespot.services.identitymanagementslavebackend.domain.model.identity.device;

import java.util.Optional;

public interface DeviceRepository {

    Device add(Device device);

    Optional<Device> findDeviceById(DeviceId id);
}
