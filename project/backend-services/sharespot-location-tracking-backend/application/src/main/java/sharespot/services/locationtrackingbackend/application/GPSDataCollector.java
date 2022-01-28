package sharespot.services.locationtrackingbackend.application;

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

    public static final int MAX_TIME_BETWEEN_INFO_IN_SECONDS = 60;
    private final ProcessedSensorDataRepository repository;

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
                .stream()
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
            var status = new StatusDataDetails(data.data.status.motion);
            return new GPSSensorDataHistoryStep(gps, status, data.reportedAt);
        }).collect(Collectors.toList()));
        history.distance = Haversine.calcDistance(dto);
        return history;
    }

    private List<GPSSensorDataHistorySegment> buildHistorySegments(List<GPSSensorDataHistoryStep> steps) {
        if (steps.size() == 1) {
            var type = "INACTIVE".equals(steps.get(0).status().motion()) ?
                    GPSSensorDataHistorySegmentType.INACTIVE : GPSSensorDataHistorySegmentType.ACTIVE;
            return List.of(new GPSSensorDataHistorySegment(type, steps));
        } else {
            //order in "In motion" and "Stoped"
            var preSegments = new ArrayList<GPSSensorDataHistorySegment>();
            for (int i = 0; i < steps.size(); i++) {
                GPSSensorDataHistoryStep first = steps.get(i);
                var type = "INACTIVE".equals(first.status().motion()) ?
                        GPSSensorDataHistorySegmentType.INACTIVE : GPSSensorDataHistorySegmentType.ACTIVE;
                var subSegment = new ArrayList<GPSSensorDataHistoryStep>();
                while (i < steps.size() && first.status().sameSegment(steps.get(i).status())) {
                    subSegment.add(steps.get(i));
                    i++;
                }
                i--;
                preSegments.add(new GPSSensorDataHistorySegment(type, subSegment));
            }
            return preSegments.stream()
                    .map(this::splitPreSegments)
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
        }
    }

    private List<GPSSensorDataHistorySegment> splitPreSegments(GPSSensorDataHistorySegment preSeg) {
        var finalList = new ArrayList<GPSSensorDataHistorySegment>();
        var temp = new ArrayList<GPSSensorDataHistoryStep>();
        Boolean isUnknown = null;

        for (int i = 0; i < preSeg.steps().size() - 1; i++) {
            GPSSensorDataHistoryStep first = preSeg.steps().get(i);
            GPSSensorDataHistoryStep second = preSeg.steps().get(i + 1);
            temp.add(first);
            var tempIsUnknown = Math.abs(first.reportedAt() - second.reportedAt()) > MAX_TIME_BETWEEN_INFO_IN_SECONDS * 1000;
            if (isUnknown == null) {
                isUnknown = tempIsUnknown;
            } else if (isUnknown != tempIsUnknown) {
                finalList.add(new GPSSensorDataHistorySegment(defineSegmentType(preSeg, isUnknown), new ArrayList<>(temp)));
                temp = new ArrayList<>();
                isUnknown = null;
                i--;
            }
        }
        if (isUnknown != null)
            finalList.add(new GPSSensorDataHistorySegment(defineSegmentType(preSeg, isUnknown), new ArrayList<>(temp)));
        return finalList;
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
