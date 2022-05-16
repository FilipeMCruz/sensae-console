package sharespot.services.dataprocessormaster.infrastructure.endpoint.amqp.model;

import sharespot.services.dataprocessormaster.application.DataTransformationNotificationDTO;

public class DataTransformationNotificationDTOImpl implements DataTransformationNotificationDTO {

    public String sensorType;

    public DataTransformationNotificationTypeDTOImpl type;

    public DataTransformationDTOImpl information;
}
