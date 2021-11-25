package sharespot.services.locationtrackingbackend.infrastructure.endpoint.amqp.dto;

import java.util.UUID;

public final class ProcessedSensorDataDTOImpl {

    public UUID dataId;

    public ProcessedSensorDTOImpl device;

    public Long reportedAt;

    public SensorDataDetailsDTOImpl data;

    public DeviceRecordDTOImpl records;

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
