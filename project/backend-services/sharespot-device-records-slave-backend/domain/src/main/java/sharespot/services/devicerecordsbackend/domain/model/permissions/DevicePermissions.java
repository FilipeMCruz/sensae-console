package sharespot.services.devicerecordsbackend.domain.model.permissions;

import sharespot.services.devicerecordsbackend.domain.model.records.DeviceId;

public record DevicePermissions(DeviceId device, Permissions permissions) {
}
