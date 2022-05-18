package sharespot.services.dataprocessor.application;

import sharespot.services.dataprocessor.domain.DataTransformationNotification;
import sharespot.services.dataprocessor.domain.SensorTypeId;

public interface DataTransformationEventMapper {

    DataTransformationNotification dtoToDomain(DataTransformationNotificationDTO dto);

    SensorTypeIdDTO domainToDto(SensorTypeId dto);
}
