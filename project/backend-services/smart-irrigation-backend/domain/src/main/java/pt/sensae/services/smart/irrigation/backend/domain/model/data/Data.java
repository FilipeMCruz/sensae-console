package pt.sensae.services.smart.irrigation.backend.domain.model.data;

import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.payload.Payload;

public record Data(DataId id, DeviceId deviceId, ReportTime reportedAt, Payload payload) {
}
