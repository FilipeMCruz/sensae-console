package pt.sensae.services.data.processor.master.backend.application;

import pt.sensae.services.data.processor.master.backend.domain.DataTransformation;
import pt.sensae.services.data.processor.master.backend.domain.SensorTypeId;

public interface DataTransformationMapper {

    DataTransformation dtoToDomain(DataTransformationDTO dto);

    DataTransformationDTO domainToDto(DataTransformation domain, Long instant);

    SensorTypeId dtoToDomain(SensorTypeIdDTO dto);

    SensorTypeIdDTO domainToDto(SensorTypeId domain);

}
