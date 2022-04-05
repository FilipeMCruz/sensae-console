package pt.sensae.services.smart.irrigation.backend.domain.model.business.garden;

import pt.sensae.services.smart.irrigation.backend.domain.model.GPSPoint;

import java.util.Set;

public record Area(Set<GPSPoint> boundaries) {
}
