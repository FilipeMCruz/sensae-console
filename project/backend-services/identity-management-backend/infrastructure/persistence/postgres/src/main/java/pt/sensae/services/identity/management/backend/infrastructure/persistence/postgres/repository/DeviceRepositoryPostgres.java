package pt.sensae.services.identity.management.backend.infrastructure.persistence.postgres.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.sensae.services.identity.management.backend.infrastructure.persistence.postgres.model.device.DevicePostgres;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface DeviceRepositoryPostgres extends CrudRepository<DevicePostgres, Long> {

    void deleteByOid(String deviceId);

    Optional<DevicePostgres> findByOid(String deviceId);

    @Query(value = "select d from device d join d.devicePermissions a where (a.domainOid IN :domainId)")
    Set<DevicePostgres> findByDomainIds(@Param("domainId") List<String> domain);
}
