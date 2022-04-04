package pt.sensae.services.smart.irrigation.backend.domain.model.garden;

import pt.sensae.services.smart.irrigation.backend.domain.model.Ownership;

public record Garden(Area area, IrrigationSystem irrigationSystem, Ownership ownership) {

}
