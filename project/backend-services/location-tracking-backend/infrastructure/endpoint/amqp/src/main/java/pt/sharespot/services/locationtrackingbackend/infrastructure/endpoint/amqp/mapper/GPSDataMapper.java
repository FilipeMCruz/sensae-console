package pt.sharespot.services.locationtrackingbackend.infrastructure.endpoint.amqp.mapper;

import pt.sharespot.services.locationtrackingbackend.domain.sensor.gps.GPSData;
import pt.sharespot.services.locationtrackingbackend.domain.sensor.gps.GPSDataDetails;
import pt.sharespot.services.locationtrackingbackend.infrastructure.endpoint.amqp.dto.GPSDataDTO;

public class GPSDataMapper {

    public static GPSData dtoToDomain(GPSDataDTO dto) {
        var details = new GPSDataDetails(dto.data.latitude, dto.data.longitude);
        return new GPSData(dto.dataId, dto.deviceId, dto.reportedAt, details);
    }
}
