package pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone;

import pt.sensae.services.smart.irrigation.backend.domain.model.GPSPoint;

public record BoundaryPoint(Integer position, GPSPoint point) {
}