package pt.sensae.services.device.management.slave.backend.infrastructure.persistence.postgres.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.sensae.services.device.management.slave.backend.infrastructure.persistence.postgres.model.DeviceInformationPostgres;

import java.util.Optional;

@Repository
public interface DeviceRepositoryPostgres extends CrudRepository<DeviceInformationPostgres, Long> {

    Optional<DeviceInformationPostgres> findByDeviceId(String deviceId);
}
