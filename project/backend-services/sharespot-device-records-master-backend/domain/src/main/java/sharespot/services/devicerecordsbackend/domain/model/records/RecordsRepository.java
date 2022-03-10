package sharespot.services.devicerecordsbackend.domain.model.records;

import sharespot.services.devicerecordsbackend.domain.model.DeviceId;

import java.util.Set;
import java.util.stream.Stream;

public interface RecordsRepository {

    DeviceRecords save(DeviceRecords records);

    Stream<DeviceRecords> findAll();

    DeviceId delete(DeviceId id);

}
