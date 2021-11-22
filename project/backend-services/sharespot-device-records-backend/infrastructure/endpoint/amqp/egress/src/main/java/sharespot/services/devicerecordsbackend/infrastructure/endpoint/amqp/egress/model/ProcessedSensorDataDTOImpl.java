package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.egress.model;

import sharespot.services.devicerecordsbackend.application.ProcessedSensorDataDTO;

public final class ProcessedSensorDataDTOImpl implements ProcessedSensorDataDTO {

    public String dataId;

    public String deviceId;

    public Long reportedAt;

    public SensorDataDetailsDTOImpl data;

    public ProcessedSensorDataDTOImpl(String dataId,
                                      String deviceId,
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
