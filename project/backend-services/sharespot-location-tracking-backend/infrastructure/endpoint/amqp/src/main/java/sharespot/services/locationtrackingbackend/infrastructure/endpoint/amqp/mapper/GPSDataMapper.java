package sharespot.services.locationtrackingbackend.infrastructure.endpoint.amqp.mapper;

import sharespot.services.locationtrackingbackend.domain.sensor.gps.GPSDataDetails;
import sharespot.services.locationtrackingbackend.domain.sensor.gps.RecordEntry;
import sharespot.services.locationtrackingbackend.domain.sensor.gps.SensorData;
import sharespot.services.locationtrackingbackend.domain.sensor.gps.SensorDataDetails;
import sharespot.services.locationtrackingbackend.infrastructure.endpoint.amqp.dto.ProcessedSensorDataDTOImpl;

import java.util.stream.Collectors;

public class GPSDataMapper {

    public static SensorData dtoToDomain(ProcessedSensorDataDTOImpl dto) {
        var details = new GPSDataDetails(dto.data.gps.latitude, dto.data.gps.longitude);
        var entries = dto.records.entry.stream().map(e -> new RecordEntry(e.label, e.content)).collect(Collectors.toSet());
        return new SensorData(dto.dataId, dto.deviceId, dto.reportedAt, new SensorDataDetails(details), entries);
    }
}
