package sharespot.services.locationtrackingbackend.application;

import org.springframework.stereotype.Service;
import sharespot.services.locationtrackingbackend.domain.ProcessedSensorDataRepository;
import sharespot.services.locationtrackingbackend.domain.exceptions.NotValidException;
import sharespot.services.locationtrackingbackend.domain.model.pastdata.GPSSensorDataFilter;
import sharespot.services.locationtrackingbackend.domain.model.pastdata.GPSSensorDataHistory;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class GPSDataCollector {

    private final ProcessedSensorDataRepository repository;

    public GPSDataCollector(ProcessedSensorDataRepository repository) {
        this.repository = repository;
    }

    public GPSSensorDataHistory history(GPSSensorDataFilter filters) {
        validate(filters);
        return repository.queryDevice(filters);
    }

    private void validate(GPSSensorDataFilter filters) {
        if (filters.device == null || filters.device.isEmpty()) {
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
