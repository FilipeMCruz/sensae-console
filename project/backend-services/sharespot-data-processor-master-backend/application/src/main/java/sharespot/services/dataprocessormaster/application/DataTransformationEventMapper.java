package sharespot.services.dataprocessormaster.application;

import sharespot.services.dataprocessormaster.domain.DataTransformation;
import sharespot.services.dataprocessormaster.domain.SensorTypeId;

public interface DataTransformationEventMapper {

    DataTransformationNotificationDTO domainToUpdatedDto(DataTransformation domain);

    DataTransformationNotificationDTO domainToDeletedDto(SensorTypeId domain);

    SensorTypeId dtoToDomain(SensorTypeIdDTO dto);
}
