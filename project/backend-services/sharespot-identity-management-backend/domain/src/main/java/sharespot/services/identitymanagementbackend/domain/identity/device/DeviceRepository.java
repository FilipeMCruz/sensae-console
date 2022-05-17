package sharespot.services.identitymanagementbackend.domain.identity.device;

import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface DeviceRepository {

    Optional<Device> findDeviceById(DeviceId id);

    Device relocateDevice(Device tenant);

    Stream<Device> getDevicesInDomain(DomainId domain);

    Device add(Device device);
}
