package sharespot.services.devicerecordsbackend.domain.model.records;

import sharespot.services.devicerecordsbackend.domain.model.device.DeviceId;

import java.util.stream.Stream;

public interface RecordsRepository {

    DeviceRecords save(DeviceRecords records);

    Stream<DeviceRecords> findAll();

    DeviceId delete(DeviceId id);

}
