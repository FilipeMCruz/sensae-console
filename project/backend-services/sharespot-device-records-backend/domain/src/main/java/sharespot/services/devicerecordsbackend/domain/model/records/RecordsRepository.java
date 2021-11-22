package sharespot.services.devicerecordsbackend.domain.model.records;

import java.util.Optional;
import java.util.Set;

public interface RecordsRepository {

    DeviceRecords save(DeviceRecords records);

    Optional<DeviceRecords> findByDeviceId(DeviceId id);

    Set<DeviceRecords> findAll();

    DeviceId delete(DeviceId id);

}
