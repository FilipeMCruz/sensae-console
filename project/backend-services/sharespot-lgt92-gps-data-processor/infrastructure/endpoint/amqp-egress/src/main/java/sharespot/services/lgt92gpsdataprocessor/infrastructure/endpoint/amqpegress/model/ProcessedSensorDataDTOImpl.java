package sharespot.services.lgt92gpsdataprocessor.infrastructure.endpoint.amqpegress.model;

import sharespot.services.lgt92gpsdataprocessor.application.OutSensorDataDTO;

import java.util.UUID;

public final class ProcessedSensorDataDTOImpl implements OutSensorDataDTO {

    public UUID dataId;

    public UUID deviceId;

    public Long reportedAt;

    public SensorDataDetailsDTOImpl data;

    public ProcessedSensorDataDTOImpl(String dataId,
                                      String deviceId,
                                      Long reportedAt,
                                      SensorDataDetailsDTOImpl data) {
        this.dataId = UUID.fromString(dataId);
        this.deviceId = UUID.fromString(deviceId);
        this.reportedAt = reportedAt;
        this.data = data;
    }

    public ProcessedSensorDataDTOImpl() {
    }
}
