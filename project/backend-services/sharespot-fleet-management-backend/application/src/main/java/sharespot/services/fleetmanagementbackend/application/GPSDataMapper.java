package sharespot.services.fleetmanagementbackend.application;

import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
import sharespot.services.fleetmanagementbackend.domain.model.GPSDataDetails;
import sharespot.services.fleetmanagementbackend.domain.model.livedata.*;

import java.util.stream.Collectors;

public class GPSDataMapper {

    public static SensorData transform(ProcessedSensorDataWithRecordsDTO dto) {
        var details = new GPSDataDetails(dto.data.gps.latitude, dto.data.gps.longitude);
        var entries = dto.device.records.entry.stream().map(e -> new RecordEntry(e.label, e.content)).collect(Collectors.toSet());
        var device = new Device(dto.device.name, dto.device.id, entries);
        var status = new StatusDataDetails(dto.data.motion.value);
        return new SensorData(dto.dataId, device, dto.reportedAt, new SensorDataDetails(details, status));
    }
}
