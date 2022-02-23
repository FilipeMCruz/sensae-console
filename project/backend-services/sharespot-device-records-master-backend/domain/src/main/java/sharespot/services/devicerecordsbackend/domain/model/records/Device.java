package sharespot.services.devicerecordsbackend.domain.model.records;

import sharespot.services.devicerecordsbackend.domain.model.DeviceId;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceName;

public record Device(DeviceId id, DeviceName name) {
}
