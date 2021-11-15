package pt.sharespot.services.lgt92gpssensorprocessor.infrastructure.endpoint.amqpegress.model;

import pt.sharespot.services.lgt92gpssensorprocessor.application.OutSensorDataDTO;

import java.util.UUID;

public record ProcessedSensorData(
        UUID dataId,
        UUID deviceId,
        Long reportedAt,
        GPSDataDetails data
) implements OutSensorDataDTO {

}
