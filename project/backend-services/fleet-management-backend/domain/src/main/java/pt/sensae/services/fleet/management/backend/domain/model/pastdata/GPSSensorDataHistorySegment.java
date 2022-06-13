package pt.sensae.services.fleet.management.backend.domain.model.pastdata;

import java.util.List;

public record GPSSensorDataHistorySegment(GPSSensorDataHistorySegmentType type, List<GPSSensorDataHistoryStep> steps) {
}
