package sharespot.services.locationtrackingbackend.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
import sharespot.services.locationtrackingbackend.domain.ProcessedSensorDataRepository;
import sharespot.services.locationtrackingbackend.domain.exceptions.NotValidException;
import sharespot.services.locationtrackingbackend.domain.model.GPSDataDetails;
import sharespot.services.locationtrackingbackend.domain.model.livedata.StatusDataDetails;
import sharespot.services.locationtrackingbackend.domain.model.pastdata.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GPSDataCollector {

    private final ProcessedSensorDataRepository repository;

    @Value("${sharespot.location.heuristic.motion.detection.distance}")
    public double DISTANCE_IN_KM;

    @Value("${sharespot.location.heuristic.complete.history.maxtime.window}")
    public int MAX_TIME_BETWEEN_INFO_IN_SECONDS;

    public GPSDataCollector(ProcessedSensorDataRepository repository) {
        this.repository = repository;
    }

    public List<GPSSensorDataHistory> history(GPSSensorDataFilter filters) {
        validate(filters);
        var data = repository.queryMultipleDevices(filters);
        return createHistories(filters, data);
    }

    public List<GPSSensorDataHistory> createHistories(GPSSensorDataFilter filters, List<ProcessedSensorDataWithRecordsDTO> dto) {
        return dto.stream()
                .collect(Collectors.groupingBy(x -> x.device.id))
                .values()
                .parallelStream()
                .map(set -> createHistory(filters, set))
                .collect(Collectors.toList());
    }

    public GPSSensorDataHistory createHistory(GPSSensorDataFilter filters, List<ProcessedSensorDataWithRecordsDTO> dto) {
        var history = new GPSSensorDataHistory();
        history.startTime = filters.startTime.getTime();
        history.endTime = filters.endTime.getTime();
        history.deviceId = dto.get(0).device.id.toString();
        history.deviceName = dto.get(0).device.name;
        history.segments = buildHistorySegments(dto.stream().map(data -> {
            var gps = new GPSDataDetails(data.data.gps.latitude, data.data.gps.longitude);
            var status = new StatusDataDetails(data.data.motion.value);
            return new GPSSensorDataHistoryStep(gps, status, data.reportedAt);
        }).collect(Collectors.toList()));
        history.distance = Haversine.calcDistance(dto);
        return history;
    }

    private GPSSensorDataHistorySegmentType determineMotion(GPSSensorDataHistoryStep step) {
        return "INACTIVE".equals(step.status().motion()) ?
                GPSSensorDataHistorySegmentType.INACTIVE : GPSSensorDataHistorySegmentType.ACTIVE;
    }

    private List<GPSSensorDataHistorySegment> buildHistorySegments(List<GPSSensorDataHistoryStep> steps) {
        //edge case
        if (steps.size() == 1) {
            var type = "INACTIVE".equals(steps.get(0).status().motion()) ?
                    GPSSensorDataHistorySegmentType.INACTIVE : GPSSensorDataHistorySegmentType.ACTIVE;
            return List.of(new GPSSensorDataHistorySegment(type, steps));
        }

        //order in "In motion" and "Stoped"
        var preSegments = new ArrayList<GPSSensorDataHistorySegment>();
        for (int i = 0; i < steps.size(); i++) {
            GPSSensorDataHistoryStep first = steps.get(i);
            var type = determineMotion(first);
            var subSegment = new ArrayList<GPSSensorDataHistoryStep>();
            if (type == GPSSensorDataHistorySegmentType.ACTIVE && i != 0) {
                //active segments should start and end in inactive points if possible, so that a continuous path is built
                subSegment.add(steps.get(i - 1));
            }
            while (i < steps.size() && first.status().sameSegment(steps.get(i).status())) {
                subSegment.add(steps.get(i));
                i++;
            }
            if (type == GPSSensorDataHistorySegmentType.ACTIVE && steps.size() > i) {
                subSegment.add(steps.get(i));
            }
            preSegments.add(new GPSSensorDataHistorySegment(type, subSegment));
        }
        return preSegments.stream()
                .map(this::splitPreSegments)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<GPSSensorDataHistorySegment> splitPreSegments(GPSSensorDataHistorySegment preSeg) {
        if (preSeg.type() == GPSSensorDataHistorySegmentType.ACTIVE) {
            return splitActivePreSegments(preSeg);
        } else {
            return splitInactivePreSegments(preSeg);
        }
    }

    private List<GPSSensorDataHistorySegment> splitInactivePreSegments(GPSSensorDataHistorySegment preSeg) {
        var finalList = new ArrayList<GPSSensorDataHistorySegment>();
        var temp = new ArrayList<GPSSensorDataHistoryStep>();

        for (int i = 0; i < preSeg.steps().size() - 1; i++) {
            GPSSensorDataHistoryStep first = preSeg.steps().get(i);
            GPSSensorDataHistoryStep second = preSeg.steps().get(i + 1);
            temp.add(first);
            var isNewSeg = isNewSeg(first, second);
            if (isNewSeg) {
                finalList.add(prepareInactiveSubSegment(temp, preSeg));
                finalList.add(createSubSegment(preSeg, List.of(first, second), true));
                temp.clear();
            }
        }
        if (temp.size() != 0) {
            finalList.add(prepareInactiveSubSegment(temp, preSeg));
        }
        return finalList;
    }

    private GPSSensorDataHistorySegment prepareInactiveSubSegment(ArrayList<GPSSensorDataHistoryStep> temp, GPSSensorDataHistorySegment preSeg) {
        if (temp.size() == 1) {
            return createSubSegment(preSeg, List.of(temp.get(0), temp.get(0)), false);
        } else {
            return createSubSegment(preSeg, List.of(temp.get(0), temp.get(temp.size() - 1)), false);
        }
    }

    private boolean isNewSeg(GPSSensorDataHistoryStep first, GPSSensorDataHistoryStep second) {
        var isUnknown = Math.abs(first.reportedAt() - second.reportedAt()) > MAX_TIME_BETWEEN_INFO_IN_SECONDS * 1000L;
        if (isUnknown) {
            return Haversine.calcHaversine(first.gps(), second.gps()) > DISTANCE_IN_KM;
        } else {
            return false;
        }
    }

    private List<GPSSensorDataHistorySegment> splitActivePreSegments(GPSSensorDataHistorySegment preSeg) {
        var finalList = new ArrayList<GPSSensorDataHistorySegment>();
        var temp = new ArrayList<GPSSensorDataHistoryStep>();
        Boolean isUnknown = null;

        for (int i = 0; i < preSeg.steps().size() - 1; i++) {
            var first = preSeg.steps().get(i);
            var second = preSeg.steps().get(i + 1);
            temp.add(first);
            var tempIsUnknown = Math.abs(first.reportedAt() - second.reportedAt()) > MAX_TIME_BETWEEN_INFO_IN_SECONDS * 1000L;
            if (isUnknown == null) {
                isUnknown = tempIsUnknown;
            } else if (isUnknown != tempIsUnknown) {
                finalList.add(createSubSegment(preSeg, temp, isUnknown));
                temp.clear();
                isUnknown = null;
                i--;
            }
        }
        if (isUnknown != null)
            finalList.add(createSubSegment(preSeg, temp, isUnknown));
        return finalList;
    }

    private GPSSensorDataHistorySegment createSubSegment(GPSSensorDataHistorySegment preSeg, List<GPSSensorDataHistoryStep> steps, Boolean isUnknown) {
        return new GPSSensorDataHistorySegment(defineSegmentType(preSeg, isUnknown), defineSegmentSteps(preSeg, steps));
    }

    private List<GPSSensorDataHistoryStep> defineSegmentSteps(GPSSensorDataHistorySegment preSeg, List<GPSSensorDataHistoryStep> steps) {
        if (preSeg.type() == GPSSensorDataHistorySegmentType.INACTIVE) {
            return List.of(steps.get(0), steps.get(steps.size() - 1));
        } else {
            return new ArrayList<>(steps);
        }
    }

    private GPSSensorDataHistorySegmentType defineSegmentType(GPSSensorDataHistorySegment preSeg, Boolean isUnknown) {
        if (isUnknown && preSeg.type() == GPSSensorDataHistorySegmentType.ACTIVE) {
            return GPSSensorDataHistorySegmentType.UNKNOWN_ACTIVE;
        } else if (!isUnknown && preSeg.type() == GPSSensorDataHistorySegmentType.ACTIVE) {
            return GPSSensorDataHistorySegmentType.ACTIVE;
        } else if (isUnknown && preSeg.type() == GPSSensorDataHistorySegmentType.INACTIVE) {
            return GPSSensorDataHistorySegmentType.UNKNOWN_INACTIVE;
        } else {
            return GPSSensorDataHistorySegmentType.INACTIVE;
        }
    }

    private void validate(GPSSensorDataFilter filters) {
        if (filters.devices == null || filters.devices.isEmpty()) {
            throw new NotValidException("A device id or name must be provided");
        }
        if (filters.startTime == null) {
            throw new NotValidException("A start date must be provided");
        }
        if (filters.endTime != null && filters.endTime.before(filters.startTime)) {
            throw new NotValidException("End date must be after the start date");
        }
        if (filters.endTime == null) {
            filters.endTime = Timestamp.from(Instant.now());
        }
    }
}
