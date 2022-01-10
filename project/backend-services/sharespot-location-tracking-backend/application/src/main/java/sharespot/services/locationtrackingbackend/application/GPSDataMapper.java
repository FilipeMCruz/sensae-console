package sharespot.services.locationtrackingbackend.application;

import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
import sharespot.services.locationtrackingbackend.domain.model.GPSDataDetails;
import sharespot.services.locationtrackingbackend.domain.model.livedata.Device;
import sharespot.services.locationtrackingbackend.domain.model.livedata.RecordEntry;
import sharespot.services.locationtrackingbackend.domain.model.livedata.SensorData;
import sharespot.services.locationtrackingbackend.domain.model.livedata.SensorDataDetails;

import java.util.stream.Collectors;

public class GPSDataMapper {
    
    public static SensorData transform(ProcessedSensorDataWithRecordsDTO dto) {
        var details = new GPSDataDetails(dto.data.gps.latitude, dto.data.gps.longitude);
        var entries = dto.device.records.entry.stream().map(e -> new RecordEntry(e.label, e.content)).collect(Collectors.toSet());
        var device = new Device(dto.device.name, dto.device.id, entries);
        return new SensorData(dto.dataId, device, dto.reportedAt, new SensorDataDetails(details));
    }
}
