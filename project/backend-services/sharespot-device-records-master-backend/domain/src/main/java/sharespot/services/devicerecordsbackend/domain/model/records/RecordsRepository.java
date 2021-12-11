package sharespot.services.devicerecordsbackend.domain.model.records;

import java.util.Set;

public interface RecordsRepository {

    DeviceRecords save(DeviceRecords records);

    Set<DeviceRecords> findAll();

    DeviceId delete(DeviceId id);

}
