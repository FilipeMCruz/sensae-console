package sharespot.services.datadecodermaster.application;

import sharespot.services.datadecodermaster.domain.DataDecoder;
import sharespot.services.datadecodermaster.domain.SensorTypeId;

public interface DataDecoderMapper {

    DataDecoder dtoToDomain(DataDecoderDTO dto);

    DataDecoderDTO domainToDto(DataDecoder domain);

    SensorTypeId dtoToDomain(SensorTypeIdDTO dto);

    SensorTypeIdDTO domainToDto(SensorTypeId domain);

}
