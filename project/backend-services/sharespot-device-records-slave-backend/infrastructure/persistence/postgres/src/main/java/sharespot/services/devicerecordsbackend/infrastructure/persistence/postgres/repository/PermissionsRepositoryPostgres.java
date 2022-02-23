package sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.model.permissions.DevicePermissionsPostgres;

@Repository
public interface PermissionsRepositoryPostgres extends CrudRepository<DevicePermissionsPostgres, Long> {

    void deleteByDeviceId(String deviceId);

}
