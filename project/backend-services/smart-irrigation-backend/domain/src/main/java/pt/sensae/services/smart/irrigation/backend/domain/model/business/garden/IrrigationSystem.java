package pt.sensae.services.smart.irrigation.backend.domain.model.business.garden;

import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;

import java.util.Set;

public record IrrigationSystem(Set<DeviceId> devices) {
}
