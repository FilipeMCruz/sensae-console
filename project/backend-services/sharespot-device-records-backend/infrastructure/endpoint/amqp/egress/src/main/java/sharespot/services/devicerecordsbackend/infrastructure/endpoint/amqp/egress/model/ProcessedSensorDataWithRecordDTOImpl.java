package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.egress.model;

import sharespot.services.devicerecordsbackend.application.ProcessedSensorDataWithRecordDTO;

import java.util.UUID;

public final class ProcessedSensorDataWithRecordDTOImpl implements ProcessedSensorDataWithRecordDTO {

    public UUID dataId;

    public UUID deviceId;

    public Long reportedAt;

    public SensorDataDetailsDTOImpl data;

    public DeviceRecordDTOImpl records;

    public ProcessedSensorDataWithRecordDTOImpl(UUID dataId,
                                                UUID deviceId,
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
