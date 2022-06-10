package pt.sensae.services.smart.irrigation.backend.application.model.data;

import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone.IrrigationZoneId;

import java.util.Set;

public record LiveDataFilter(
        Set<IrrigationZoneId> irrigationZones,
        Set<DeviceId> devices,
        String content) {
}
