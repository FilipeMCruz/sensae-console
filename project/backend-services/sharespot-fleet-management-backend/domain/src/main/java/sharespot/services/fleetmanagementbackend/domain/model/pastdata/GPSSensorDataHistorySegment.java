package sharespot.services.fleetmanagementbackend.domain.model.pastdata;

import java.util.List;

public record GPSSensorDataHistorySegment(GPSSensorDataHistorySegmentType type, List<GPSSensorDataHistoryStep> steps) {
}