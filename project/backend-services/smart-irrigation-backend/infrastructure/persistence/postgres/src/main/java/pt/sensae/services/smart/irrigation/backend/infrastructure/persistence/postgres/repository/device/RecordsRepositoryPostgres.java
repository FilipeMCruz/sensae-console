package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.repository.device;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres.model.device.DeviceRecordsPostgres;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface RecordsRepositoryPostgres extends CrudRepository<DeviceRecordsPostgres, Long> {

    Stream<DeviceRecordsPostgres> getAllByEntryPersistenceId(Long persistenceId);

    Stream<DeviceRecordsPostgres> getAllByEntryPersistenceIdIsIn(List<Long> persistenceId);
}