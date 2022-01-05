package sharespot.services.locationtrackingbackend.infrastructure.endpoint.graphql.mapper;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
import sharespot.services.locationtrackingbackend.application.SensorDataMapper;
import sharespot.services.locationtrackingbackend.infrastructure.endpoint.graphql.model.livedata.*;

import java.util.stream.Collectors;

@Service
public class GPSDataMapper implements SensorDataMapper {
    public SensorData transform(ProcessedSensorDataWithRecordsDTO dto) {
        var details = new GPSDataDetails(dto.data.gps.latitude, dto.data.gps.longitude);
        var entries = dto.device.records.entry.stream().map(e -> new RecordEntry(e.label, e.content)).collect(Collectors.toSet());
        var device = new Device(dto.device.name, dto.device.id, entries);
        return new SensorData(dto.dataId, device, dto.reportedAt, new SensorDataDetails(details));
    }
}
