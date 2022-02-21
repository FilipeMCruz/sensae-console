package sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sharespot.services.identitymanagementbackend.infrastructure.persistence.postgres.model.device.DevicePostgres;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepositoryPostgres extends CrudRepository<DevicePostgres, Long> {

    void deleteByOid(String deviceId);

    Optional<DevicePostgres> findByOid(String deviceId);

    @Query(value = "select d from device d join d.devicePermissions a where (a.domainOid = :domainId)")
    List<DevicePostgres> findByDomainId(@Param("domainId") String domain);
}
