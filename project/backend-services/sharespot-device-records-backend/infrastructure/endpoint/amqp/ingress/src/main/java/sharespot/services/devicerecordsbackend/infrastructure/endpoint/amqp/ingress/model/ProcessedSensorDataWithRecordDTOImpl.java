package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.ingress.model;

import sharespot.services.devicerecordsbackend.application.ProcessedSensorDataWithRecordDTO;

public final class ProcessedSensorDataWithRecordDTOImpl implements ProcessedSensorDataWithRecordDTO {

    public String dataId;

    public String deviceId;

    public Long reportedAt;

    public SensorDataDetailsDTOImpl data;

    public DeviceRecordDTOImpl records;

    public ProcessedSensorDataWithRecordDTOImpl(String dataId,
                                                String deviceId,
                                                Long reportedAt,
                                                SensorDataDetailsDTOImpl data,
                                                DeviceRecordDTOImpl records) {
        this.dataId = dataId;
        this.deviceId = deviceId;
        this.reportedAt = reportedAt;
        this.data = data;
        this.records = records;
    }

    public ProcessedSensorDataWithRecordDTOImpl() {
    }
}
