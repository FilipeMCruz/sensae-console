package pt.sharespot.services.lgt92gpssensorprocessor.infrastructure.endpoint.amqpegress.model.gps;

import pt.sharespot.services.lgt92gpssensorprocessor.application.OutSensorDataDTO;

import java.util.UUID;

public record GPSData(UUID dataId, UUID deviceId, Long reportedAt,
                      GPSDataDetails data) implements OutSensorDataDTO {

}
