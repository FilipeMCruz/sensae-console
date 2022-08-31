package pt.sensae.services.fleet.management.backend.application;

import pt.sensae.services.fleet.management.backend.domain.model.livedata.*;
import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sensae.services.fleet.management.backend.domain.model.GPSDataDetails;

import java.util.stream.Collectors;

public class GPSDataMapper {

    public static SensorData transform(DataUnitDTO dto) {
        var details = new GPSDataDetails(dto.getSensorData().gps.latitude, dto.getSensorData().gps.longitude);
        var entries = dto.device.records.stream()
                .map(e -> new RecordEntry(e.label, e.content))
                .collect(Collectors.toSet());
        var device = new Device(dto.device.name, dto.device.id, entries);
        var status = new StatusDataDetails(dto.getSensorData().motion.value);
        return new SensorData(dto.dataId, device, dto.reportedAt, new SensorDataDetails(details, status));
    }
}
