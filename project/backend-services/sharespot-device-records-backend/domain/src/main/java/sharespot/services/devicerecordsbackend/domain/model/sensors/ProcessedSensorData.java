package sharespot.services.devicerecordsbackend.domain.model.sensors;

public class ProcessedSensorData {

    private final String dataId;
    private final String deviceId;
    private final Long reportedAt;
    private final SensorDataDetails data;

    public ProcessedSensorData(
            String dataId,
            String deviceId,
            Long reportedAt,
            SensorDataDetails data
    ) {
        this.dataId = dataId;
        this.deviceId = deviceId;
        this.reportedAt = reportedAt;
        this.data = data;
    }

    public String getDataId() {
        return dataId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Long getReportedAt() {
        return reportedAt;
    }

    public SensorDataDetails getData() {
        return data;
    }
}
