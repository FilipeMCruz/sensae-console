package sharespot.services.locationtrackingbackend.application;

import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
import sharespot.services.locationtrackingbackend.domain.model.GPSDataDetails;
import sharespot.services.locationtrackingbackend.domain.model.livedata.*;

import java.util.stream.Collectors;

public class GPSDataMapper {

    public static SensorData transform(ProcessedSensorDataWithRecordsDTO dto) {
        var status = new StatusDataDetails("UNKNOWN");
        return getSensorData(dto, status);
    }

    public static SensorData transform(ProcessedSensorDataWithRecordsDTO dto, boolean isMoving) {
        var status = isMoving ? new StatusDataDetails("ACTIVE") : new StatusDataDetails("INACTIVE");
        return getSensorData(dto, status);
    }

    private static SensorData getSensorData(ProcessedSensorDataWithRecordsDTO dto, StatusDataDetails status) {
        var details = new GPSDataDetails(dto.data.gps.latitude, dto.data.gps.longitude);
        var entries = dto.device.records.entry.stream().map(e -> new RecordEntry(e.label, e.content)).collect(Collectors.toSet());
        var device = new Device(dto.device.name, dto.device.id, entries);
        return new SensorData(dto.dataId, device, dto.reportedAt, new SensorDataDetails(details, status));
    }
}
