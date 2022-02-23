package sharespot.services.devicerecordsbackend.domain.model;

import sharespot.services.devicerecordsbackend.domain.model.records.DeviceId;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceName;

public record Device(DeviceId id, DeviceName name) {
}
