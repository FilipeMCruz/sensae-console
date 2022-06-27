package pt.sensae.services.data.decoder.master.backend.application;

import pt.sensae.services.data.decoder.master.backend.domain.DataDecoder;
import pt.sensae.services.data.decoder.master.backend.domain.SensorTypeId;

public interface DataDecoderMapper {

    DataDecoder dtoToDomain(DataDecoderDTO dto);

    DataDecoderDTO domainToDto(DataDecoder domain, Long instant);

    SensorTypeId dtoToDomain(SensorTypeIdDTO dto);

    SensorTypeIdDTO domainToDto(SensorTypeId domain);

}
