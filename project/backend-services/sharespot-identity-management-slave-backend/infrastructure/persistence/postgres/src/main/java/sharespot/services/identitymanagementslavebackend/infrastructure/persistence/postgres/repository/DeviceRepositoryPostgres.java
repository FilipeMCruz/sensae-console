package sharespot.services.identitymanagementslavebackend.infrastructure.persistence.postgres.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sharespot.services.identitymanagementslavebackend.infrastructure.persistence.postgres.model.device.DevicePostgres;

import java.util.Optional;

@Repository
public interface DeviceRepositoryPostgres extends CrudRepository<DevicePostgres, Long> {

    Optional<DevicePostgres> findByOid(String deviceId);
}
