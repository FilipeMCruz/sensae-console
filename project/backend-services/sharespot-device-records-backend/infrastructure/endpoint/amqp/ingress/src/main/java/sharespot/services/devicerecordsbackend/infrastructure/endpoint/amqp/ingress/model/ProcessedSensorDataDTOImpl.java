package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.ingress.model;

import sharespot.services.devicerecordsbackend.application.ProcessedSensorDataDTO;

import java.util.UUID;

public final class ProcessedSensorDataDTOImpl implements ProcessedSensorDataDTO {

    public UUID dataId;

    public UUID deviceId;

    public Long reportedAt;

    public SensorDataDetailsDTOImpl data;

    public ProcessedSensorDataDTOImpl(UUID dataId,
                                      UUID deviceId,
                                      Long reportedAt,
                                      SensorDataDetailsDTOImpl data) {
        this.dataId = dataId;
        this.deviceId = deviceId;
        this.reportedAt = reportedAt;
        this.data = data;
    }

    public ProcessedSensorDataDTOImpl() {
    }
}
