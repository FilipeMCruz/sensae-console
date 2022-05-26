package sharespot.services.fleetmanagementbackend.application;

import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import sharespot.services.fleetmanagementbackend.domain.model.GPSDataDetails;
import sharespot.services.fleetmanagementbackend.domain.model.livedata.*;

import java.util.stream.Collectors;

public class GPSDataMapper {

    public static SensorData transform(SensorDataDTO dto) {
        var details = new GPSDataDetails(dto.getSensorData().gps.latitude, dto.getSensorData().gps.longitude);
        var entries = dto.device.records.stream()
                .map(e -> new RecordEntry(e.label, e.content))
                .collect(Collectors.toSet());
        var device = new Device(dto.device.name, dto.device.id, entries);
        var status = new StatusDataDetails(dto.getSensorData().motion.value);
        return new SensorData(dto.dataId, device, dto.reportedAt, new SensorDataDetails(details, status));
    }
}
