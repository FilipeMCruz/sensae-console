package sharespot.services.devicerecordsbackend.domain.model.records;

import sharespot.services.devicerecordsbackend.domain.model.device.DeviceId;

public record Device(DeviceId id, DeviceName name) {
}
