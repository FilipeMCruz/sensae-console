package pt.sensae.services.device.commander.backend.infrastructure.persistence.postgres.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.sensae.services.device.commander.backend.infrastructure.persistence.postgres.model.DeviceInformationPostgres;

import java.util.Optional;

@Repository
public interface DeviceRepositoryPostgres extends CrudRepository<DeviceInformationPostgres, Long> {

    Optional<DeviceInformationPostgres> findByDeviceId(String deviceId);
}
