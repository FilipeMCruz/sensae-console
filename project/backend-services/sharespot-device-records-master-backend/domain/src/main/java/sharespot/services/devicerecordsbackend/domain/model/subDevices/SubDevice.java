package sharespot.services.devicerecordsbackend.domain.model.subDevices;

import sharespot.services.devicerecordsbackend.domain.model.device.DeviceId;

public record SubDevice(DeviceId id, DeviceRef ref) {
}
