package sharespot.services.locationtrackingbackend.infrastructure.endpoint.amqp.mapper;

import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
import sharespot.services.locationtrackingbackend.domain.sensor.gps.*;

import java.util.stream.Collectors;

public class GPSDataMapper {
    public static SensorData dtoToDomain(ProcessedSensorDataWithRecordsDTO dto) {
        var details = new GPSDataDetails(dto.data.gps.latitude, dto.data.gps.longitude);
        var entries = dto.device.records.entry.stream().map(e -> new RecordEntry(e.label, e.content)).collect(Collectors.toSet());
        var device = new Sensor(dto.device.name, dto.device.id);
        return new SensorData(dto.dataId, device, dto.reportedAt, new SensorDataDetails(details), entries);
    }
}
