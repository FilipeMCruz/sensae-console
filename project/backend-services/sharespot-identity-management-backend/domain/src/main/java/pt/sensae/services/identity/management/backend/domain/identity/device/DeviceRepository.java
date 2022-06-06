package pt.sensae.services.identity.management.backend.domain.identity.device;

import pt.sensae.services.identity.management.backend.domain.identity.domain.DomainId;

import java.util.Optional;
import java.util.stream.Stream;

public interface DeviceRepository {

    Optional<Device> findDeviceById(DeviceId id);

    Device relocateDevice(Device tenant);

    Stream<Device> getDevicesInDomains(Stream<DomainId> domain);

    Device add(Device device);

    Stream<Device> findAll();
}
