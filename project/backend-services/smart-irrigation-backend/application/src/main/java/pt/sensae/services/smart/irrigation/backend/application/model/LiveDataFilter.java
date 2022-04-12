package pt.sensae.services.smart.irrigation.backend.application.model;

import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningAreaId;

import java.util.Set;

public record LiveDataFilter(
        Set<GardeningAreaId> gardens,
        Set<DeviceId> devices,
        String content) {
}
