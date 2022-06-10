package pt.sensae.services.fleet.management.backend.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pt.sensae.services.fleet.management.backend.domain.model.pastdata.*;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sensae.services.fleet.management.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.fleet.management.backend.application.auth.TokenExtractor;
import pt.sensae.services.fleet.management.backend.application.auth.UnauthorizedException;
import pt.sensae.services.fleet.management.backend.domain.SensorDataRepository;
import pt.sensae.services.fleet.management.backend.domain.exceptions.NotValidException;
import pt.sensae.services.fleet.management.backend.domain.model.GPSDataDetails;
import pt.sensae.services.fleet.management.backend.domain.model.domain.DomainId;
import pt.sensae.services.fleet.management.backend.domain.model.livedata.StatusDataDetails;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GPSDataCollector {

    private final SensorDataRepository repository;

    private final TokenExtractor authHandler;

    @Value("${sensae.location.heuristic.motion.detection.distance}")
    public double DISTANCE_IN_KM;

    @Value("${sensae.location.heuristic.complete.history.maxtime.window}")
    public int MAX_TIME_BETWEEN_INFO_IN_SECONDS;

    public GPSDataCollector(SensorDataRepository repository, TokenExtractor authHandler) {
        this.repository = repository;
        this.authHandler = authHandler;
    }

    public List<GPSSensorDataHistory> history(GPSSensorDataFilter filters, AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("fleet_management:past_data:read"))
            throw new UnauthorizedException("No Permissions");

        validate(filters);

        var domains = extract.domains.stream()
                .distinct()
                .map(d -> DomainId.of(UUID.fromString(d)));

        var data = repository.queryMultipleDevices(filters, domains);
        return createHistories(filters, data);
    }

    public List<GPSSensorDataHistory> createHistories(GPSSensorDataFilter filters, Stream<SensorDataDTO> dto) {
        return dto.collect(Collectors.groupingBy(x -> x.device.id))
                .values()
                .parallelStream()
                .map(set -> createHistory(filters, set))
                .collect(Collectors.toList());
    }

    public GPSSensorDataHistory createHistory(GPSSensorDataFilter filters, List<SensorDataDTO> dto) {
        var history = new GPSSensorDataHistory();
        history.startTime = filters.startTime.getTime();
        history.endTime = filters.endTime.getTime();
        history.deviceId = dto.get(0).device.id.toString();
        history.deviceName = dto.get(0).device.name;
        history.segments = buildHistorySegments(dto.stream().map(data -> {
            var gps = new GPSDataDetails(data.getSensorData().gps.latitude, data.getSensorData().gps.longitude);
            var status = new StatusDataDetails(data.getSensorData().motion.value);
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

        //order in "In motion" and "Stopped"
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
            var first = preSeg.steps().get(i);
            var second = preSeg.steps().get(i + 1);
            temp.add(first);
            if (isNewSeg(first, second)) {
                finalList.add(createSubSegment(preSeg, List.of(temp.get(0), temp.get(temp.size() - 1)), false));
                finalList.add(createSubSegment(preSeg, List.of(first, second), true));
                temp.clear();
            }
        }
        if (temp.size() != 0) {
            finalList.add(createSubSegment(preSeg, List.of(temp.get(0), temp.get(temp.size() - 1)), false));
        }
        return finalList;
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
        if (GPSSensorDataHistorySegmentType.INACTIVE == preSeg.type()) {
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