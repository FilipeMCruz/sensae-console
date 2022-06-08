package pt.sensae.services.device.ownership.backend.domain.device;

import java.util.Optional;

public interface DeviceRepository {

    Device add(Device device);

    Optional<Device> findDeviceById(DeviceId id);
}
