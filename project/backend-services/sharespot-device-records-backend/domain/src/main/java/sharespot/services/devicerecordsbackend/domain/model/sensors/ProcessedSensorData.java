package sharespot.services.devicerecordsbackend.domain.model.sensors;

import java.util.UUID;

public class ProcessedSensorData {

    private final UUID dataId;
    private final UUID deviceId;
    private final Long reportedAt;
    private final SensorDataDetails data;

    public ProcessedSensorData(
            UUID dataId,
            UUID deviceId,
            Long reportedAt,
            SensorDataDetails data
    ) {
        this.dataId = dataId;
        this.deviceId = deviceId;
        this.reportedAt = reportedAt;
        this.data = data;
    }

    public UUID getDataId() {
        return dataId;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public Long getReportedAt() {
        return reportedAt;
    }

    public SensorDataDetails getData() {
        return data;
    }
}
