package sharespot.services.devicerecordsbackend.infrastructure.endpoint.amqp.egress.model;

import sharespot.services.devicerecordsbackend.application.ProcessedSensorDataWithRecordDTO;

import java.util.UUID;

public final class ProcessedSensorDataWithRecordDTOImpl implements ProcessedSensorDataWithRecordDTO {

    public UUID dataId;

    public ProcessedSensorDTOWithRecordsImpl device;

    public Long reportedAt;

    public SensorDataDetailsDTOImpl data;

    public DeviceRecordDTOImpl records;

    public ProcessedSensorDataWithRecordDTOImpl(UUID dataId,
                                                ProcessedSensorDTOWithRecordsImpl device,
                                                Long reportedAt,
                                                SensorDataDetailsDTOImpl data,
                                                DeviceRecordDTOImpl records) {
        this.dataId = dataId;
        this.device = device;
        this.reportedAt = reportedAt;
        this.data = data;
        this.records = records;
    }

    public ProcessedSensorDataWithRecordDTOImpl() {
    }

    @Override
    public UUID dataId() {
        return dataId;
    }

    @Override
    public boolean hasGpsData() {
        return this.data.gps.exists();
    }

    @Override
    public boolean hasTempCData() {
        return false;
    }
}
