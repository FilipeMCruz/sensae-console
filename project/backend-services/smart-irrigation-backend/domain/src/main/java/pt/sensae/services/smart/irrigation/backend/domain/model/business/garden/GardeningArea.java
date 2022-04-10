package pt.sensae.services.smart.irrigation.backend.domain.model.business.garden;

public record GardeningArea(GardeningAreaId id, GardenName name, GardeningAreaType type, Area area,
                            IrrigationSystem valves) {

}
