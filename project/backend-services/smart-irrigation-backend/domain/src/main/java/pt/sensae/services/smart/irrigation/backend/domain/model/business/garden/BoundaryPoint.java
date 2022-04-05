package pt.sensae.services.smart.irrigation.backend.domain.model.business.garden;

import pt.sensae.services.smart.irrigation.backend.domain.model.GPSPoint;

public record BoundaryPoint(int position, GPSPoint point) {
}
