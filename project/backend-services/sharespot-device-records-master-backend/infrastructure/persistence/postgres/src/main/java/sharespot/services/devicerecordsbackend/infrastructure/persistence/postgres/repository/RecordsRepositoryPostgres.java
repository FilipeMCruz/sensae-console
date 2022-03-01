package sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sharespot.services.devicerecordsbackend.infrastructure.persistence.postgres.model.DeviceRecordsPostgres;

import java.util.Optional;

@Repository
public interface RecordsRepositoryPostgres extends CrudRepository<DeviceRecordsPostgres, Long> {

    Optional<DeviceRecordsPostgres> findByDeviceId(String deviceId);

    void deleteByDeviceId(String deviceId);
}
