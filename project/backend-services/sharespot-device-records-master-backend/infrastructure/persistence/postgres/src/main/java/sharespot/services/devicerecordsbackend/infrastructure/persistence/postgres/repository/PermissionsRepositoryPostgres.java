package sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.model.permissions.DevicePermissionsPostgres;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionsRepositoryPostgres extends CrudRepository<DevicePermissionsPostgres, Long> {

    Optional<DevicePermissionsPostgres> findByDeviceId(String deviceId);


    @Query(value = "select d from permission d join d.entries a where (a.domainId = :domainId)")
    List<DevicePermissionsPostgres> findByDomainId(@Param("domainId") String domain);
}
