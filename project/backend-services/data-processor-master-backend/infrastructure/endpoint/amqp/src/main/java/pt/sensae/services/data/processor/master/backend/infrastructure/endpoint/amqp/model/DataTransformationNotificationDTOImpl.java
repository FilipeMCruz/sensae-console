package pt.sensae.services.data.processor.master.backend.infrastructure.endpoint.amqp.model;

import pt.sensae.services.data.processor.master.backend.application.DataTransformationNotificationDTO;

public class DataTransformationNotificationDTOImpl implements DataTransformationNotificationDTO {

    public String sensorType;

    public DataTransformationNotificationTypeDTOImpl type;

    public DataTransformationDTOImpl information;
}
