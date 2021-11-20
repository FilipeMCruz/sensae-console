package  sharespot.services.lgt92gpsdataprocessor.infrastructure.endpoint.amqpegress.model;

import  sharespot.services.lgt92gpsdataprocessor.application.OutSensorDataDTO;

public record ProcessedSensorData(
        String dataId,
        String deviceId,
        Long reportedAt,
        SensorDataDetails data
) implements OutSensorDataDTO {

}
