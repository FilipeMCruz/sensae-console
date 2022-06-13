package pt.sensae.services.data.processor.master.backend.application;

import pt.sensae.services.data.processor.master.backend.domain.DataTransformation;
import pt.sensae.services.data.processor.master.backend.domain.SensorTypeId;

public interface DataTransformationEventMapper {

    DataTransformationNotificationDTO domainToUpdatedDto(DataTransformation domain);

    DataTransformationNotificationDTO domainToDeletedDto(SensorTypeId domain);

    SensorTypeId dtoToDomain(SensorTypeIdDTO dto);
}
