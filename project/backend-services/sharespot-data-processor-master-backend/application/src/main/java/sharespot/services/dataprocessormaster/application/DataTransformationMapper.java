package sharespot.services.dataprocessormaster.application;

import sharespot.services.dataprocessormaster.domain.DataTransformation;
import sharespot.services.dataprocessormaster.domain.SensorTypeId;

public interface DataTransformationMapper {

    DataTransformation dtoToDomain(DataTransformationDTO dto);

    DataTransformationDTO domainToDto(DataTransformation domain);

    SensorTypeId dtoToDomain(SensorTypeIdDTO dto);

    SensorTypeIdDTO domainToDto(SensorTypeId domain);

}
