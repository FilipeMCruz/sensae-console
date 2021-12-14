package sharespot.services.lgt92gpsdataprocessor.infrastructure.endpoint.amqpegress.model;

import sharespot.services.lgt92gpsdataprocessor.application.OutSensorDataDTO;

import java.util.UUID;

public final class ProcessedSensorDataDTOImpl implements OutSensorDataDTO {

    public UUID dataId;

    public ProcessedSensorDTOImpl device;

    public Long reportedAt;

    public SensorDataDetailsDTOImpl data;

    public ProcessedSensorDataDTOImpl(String dataId,
                                      ProcessedSensorDTOImpl device,
                                      Long reportedAt,
                                      SensorDataDetailsDTOImpl data) {
        this.dataId = UUID.fromString(dataId);
        this.device = device;
        this.reportedAt = reportedAt;
        this.data = data;
    }

    public ProcessedSensorDataDTOImpl() {
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
