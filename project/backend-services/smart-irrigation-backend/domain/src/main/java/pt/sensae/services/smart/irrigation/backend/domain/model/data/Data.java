package pt.sensae.services.smart.irrigation.backend.domain.model.data;

import pt.sensae.services.smart.irrigation.backend.domain.model.device.DeviceId;

public record Data(DataId id, DeviceId deviceId, ReportTime reportedAt, Payload payload) {
}
