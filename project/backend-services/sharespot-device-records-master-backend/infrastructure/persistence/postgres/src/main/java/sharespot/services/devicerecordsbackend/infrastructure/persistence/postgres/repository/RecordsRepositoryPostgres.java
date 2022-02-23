package sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.model.records.DeviceRecordsPostgres;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecordsRepositoryPostgres extends CrudRepository<DeviceRecordsPostgres, Long> {

    Optional<DeviceRecordsPostgres> findByDeviceId(String deviceId);
}
