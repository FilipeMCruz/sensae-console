package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.ingress.model;

import sharespot.services.devicerecordsbackend.application.ProcessedSensorDataDTO;

import java.util.UUID;

public final class ProcessedSensorDataDTOImpl implements ProcessedSensorDataDTO {

    public UUID dataId;

    public ProcessedSensorDTOImpl device;

    public Long reportedAt;

    public SensorDataDetailsDTOImpl data;

    public ProcessedSensorDataDTOImpl(UUID dataId,
                                      ProcessedSensorDTOImpl device,
                                      Long reportedAt,
                                      SensorDataDetailsDTOImpl data) {
        this.dataId = dataId;
        this.device = device;
        this.reportedAt = reportedAt;
        this.data = data;
    }

    public ProcessedSensorDataDTOImpl() {
    }
}
