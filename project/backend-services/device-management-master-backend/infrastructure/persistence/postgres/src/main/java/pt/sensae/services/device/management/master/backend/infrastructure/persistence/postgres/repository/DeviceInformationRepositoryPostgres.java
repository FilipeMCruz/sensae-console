package pt.sensae.services.device.management.master.backend.infrastructure.persistence.postgres.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.sensae.services.device.management.master.backend.infrastructure.persistence.postgres.model.DeviceInformationPostgres;

import java.util.Optional;
import java.util.Set;

@Repository
public interface DeviceInformationRepositoryPostgres extends CrudRepository<DeviceInformationPostgres, Long> {

    Optional<DeviceInformationPostgres> findByDeviceId(String deviceId);

    Set<DeviceInformationPostgres> findAllByDeviceIdIn(Set<String> deviceId);

    void deleteByDeviceId(String deviceId);
}
