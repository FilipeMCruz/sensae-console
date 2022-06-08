package pt.sensae.services.data.processor.slave.backend.application;

import pt.sensae.services.data.processor.slave.backend.domain.DataTransformationNotification;
import pt.sensae.services.data.processor.slave.backend.domain.SensorTypeId;

public interface DataTransformationEventMapper {

    DataTransformationNotification dtoToDomain(DataTransformationNotificationDTO dto);

    SensorTypeIdDTO domainToDto(SensorTypeId dto);
}
