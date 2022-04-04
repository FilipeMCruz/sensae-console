package pt.sensae.services.smart.irrigation.backend.domain.model.data;

import pt.sensae.services.smart.irrigation.backend.domain.model.data.payload.Illuminance;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.payload.SoilMoisture;

public record Payload(Illuminance illuminance, SoilMoisture soilMoisture) {
}
