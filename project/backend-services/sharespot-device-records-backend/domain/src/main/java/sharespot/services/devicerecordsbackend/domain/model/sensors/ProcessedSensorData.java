package sharespot.services.devicerecordsbackend.domain.model.sensors;

import java.util.UUID;

public class ProcessedSensorData {

    private final UUID dataId;
    private final ProcessedSensor device;
    private final Long reportedAt;
    private final SensorDataDetails data;

    public ProcessedSensorData(
            UUID dataId,
            ProcessedSensor device,
            Long reportedAt,
            SensorDataDetails data
    ) {
        this.dataId = dataId;
        this.device = device;
        this.reportedAt = reportedAt;
        this.data = data;
    }

    public UUID getDataId() {
        return dataId;
    }

    public ProcessedSensor getDevice() {
        return device;
    }

    public Long getReportedAt() {
        return reportedAt;
    }

    public SensorDataDetails getData() {
        return data;
    }
}
