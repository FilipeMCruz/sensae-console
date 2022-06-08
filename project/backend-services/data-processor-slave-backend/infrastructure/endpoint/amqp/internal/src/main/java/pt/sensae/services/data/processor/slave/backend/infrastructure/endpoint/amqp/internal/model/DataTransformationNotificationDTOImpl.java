package pt.sensae.services.data.processor.slave.backend.infrastructure.endpoint.amqp.internal.model;

import pt.sensae.services.data.processor.slave.backend.application.DataTransformationNotificationDTO;

public class DataTransformationNotificationDTOImpl implements DataTransformationNotificationDTO {

    public String sensorType;

    public DataTransformationNotificationTypeDTOImpl type;

    public DataTransformationDTOImpl information;
}
