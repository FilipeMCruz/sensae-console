package sharespot.services.locationtrackingbackend.application;

import org.springframework.stereotype.Service;
import sharespot.services.locationtrackingbackend.domain.ProcessedSensorDataRepository;
import sharespot.services.locationtrackingbackend.domain.exceptions.NotValidException;
import sharespot.services.locationtrackingbackend.domain.model.pastdata.GPSSensorDataHistory;
import sharespot.services.locationtrackingbackend.domain.model.pastdata.GPSSensorDataQuery;

import java.time.Instant;

@Service
public class GPSDataCollector {

    private final ProcessedSensorDataRepository repository;

    public GPSDataCollector(ProcessedSensorDataRepository repository) {
        this.repository = repository;
    }

    public GPSSensorDataHistory history(GPSSensorDataQuery filters) {
        validate(filters);
        return repository.queryDevice(filters);
    }

    private void validate(GPSSensorDataQuery filters) {
        if (filters.device == null || filters.device.isEmpty()) {
            throw new NotValidException("A device id or name must be provided");
        }
        if (filters.startTime == null) {
            throw new NotValidException("A start date must be provided");
        }
        if (filters.endTime != null && filters.endTime < filters.startTime) {
            throw new NotValidException("End date must be after the start date");
        }
        if (filters.endTime == null || filters.endTime == 0) {
            filters.endTime = Instant.now().toEpochMilli() * 100 + 1;
        }
    }
}
