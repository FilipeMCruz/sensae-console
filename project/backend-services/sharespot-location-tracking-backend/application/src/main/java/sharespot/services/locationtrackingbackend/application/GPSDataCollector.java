package sharespot.services.locationtrackingbackend.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
import sharespot.services.locationtrackingbackend.domain.ProcessedSensorDataRepository;
import sharespot.services.locationtrackingbackend.domain.exceptions.NotValidException;
import sharespot.services.locationtrackingbackend.domain.model.GPSDataDetails;
import sharespot.services.locationtrackingbackend.domain.model.pastdata.GPSSensorDataFilter;
import sharespot.services.locationtrackingbackend.domain.model.pastdata.GPSSensorDataHistory;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GPSDataCollector {

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
        if (dto.size() != 0) {
            history.deviceId = dto.get(0).device.id.toString();
            history.deviceName = dto.get(0).device.name;
            history.data = dto.stream().map(data -> new GPSDataDetails(data.data.gps.latitude, data.data.gps.longitude)).collect(Collectors.toList());
        }
        history.distance = Haversine.calcDistance(dto);
        return history;
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
