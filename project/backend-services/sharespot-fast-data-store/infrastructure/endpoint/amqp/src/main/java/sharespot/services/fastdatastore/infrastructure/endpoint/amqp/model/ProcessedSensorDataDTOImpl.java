package sharespot.services.fastdatastore.infrastructure.endpoint.amqp.model;

import sharespot.services.fastdatastore.application.ProcessedSensorDataDTO;

import java.util.UUID;

public final class ProcessedSensorDataDTOImpl implements ProcessedSensorDataDTO {

    public UUID dataId;

    public ProcessedSensorDTOImpl device;

    public Long reportedAt;

    public SensorDataDetailsDTOImpl data;
}
