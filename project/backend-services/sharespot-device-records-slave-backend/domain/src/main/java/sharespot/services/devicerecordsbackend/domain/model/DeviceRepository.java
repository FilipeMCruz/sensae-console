package sharespot.services.devicerecordsbackend.domain.model;

import sharespot.services.devicerecordsbackend.domain.model.device.DeviceId;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceRecords;

import java.util.Optional;

public interface DeviceRepository {

    Optional<DeviceRecords> findByDeviceId(DeviceId id);

    DeviceRecords add(DeviceRecords domain);
}
