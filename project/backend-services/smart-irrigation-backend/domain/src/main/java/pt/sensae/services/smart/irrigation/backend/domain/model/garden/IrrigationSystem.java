package pt.sensae.services.smart.irrigation.backend.domain.model.garden;

import pt.sensae.services.smart.irrigation.backend.domain.model.device.Device;

import java.util.Set;

public record IrrigationSystem(Set<Device> valves) {

}
