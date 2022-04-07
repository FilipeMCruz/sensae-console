package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.DevicePostgres;

@Repository
public interface DeviceRepositoryPostgres extends CrudRepository<DevicePostgres, Long> {
}
