package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.DeviceRecordsPostgres;

import java.util.stream.Stream;

@Repository
public interface RecordsRepositoryPostgres extends CrudRepository<DeviceRecordsPostgres, Long> {
    
    Stream<DeviceRecordsPostgres> getAllByEntryPersistenceId(Long persistenceId);
}
