package pt.sensae.services.data.processor.flow.application.mapper;

import pt.sensae.services.data.processor.flow.application.model.DataTransformationNotificationDTO;
import pt.sensae.services.data.processor.flow.application.model.SensorTypeIdDTO;
import pt.sensae.services.data.processor.flow.domain.DataTransformationNotification;
import pt.sensae.services.data.processor.flow.domain.SensorTypeId;

public interface DataTransformationEventMapper {

    DataTransformationNotification dtoToDomain(DataTransformationNotificationDTO dto);

    SensorTypeIdDTO domainToDto(SensorTypeId dto);
}
