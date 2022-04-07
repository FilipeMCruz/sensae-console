package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.LedgerEntryPostgres;

import java.util.Optional;

@Repository
public interface LedgerRepositoryPostgres extends CrudRepository<LedgerEntryPostgres, Long> {
    
    Optional<LedgerEntryPostgres> findByDeviceIdAndCloseAtNull(String deviceId);
}
