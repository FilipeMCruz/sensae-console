package sharespot.services.locationtrackingbackend.infrastructure.endpoint.amqp.dto;

import java.util.UUID;

public final class ProcessedSensorDataDTOImpl {

    public UUID dataId;

    public UUID deviceId;

    public Long reportedAt;

    public SensorDataDetailsDTOImpl data;

    public DeviceRecordDTOImpl records;

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
