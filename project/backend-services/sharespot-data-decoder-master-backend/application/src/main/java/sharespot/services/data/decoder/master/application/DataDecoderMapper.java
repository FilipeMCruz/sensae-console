package sharespot.services.data.decoder.master.application;

import sharespot.services.data.decoder.master.domain.DataDecoder;
import sharespot.services.data.decoder.master.domain.SensorTypeId;

public interface DataDecoderMapper {

    DataDecoder dtoToDomain(DataDecoderDTO dto);

    DataDecoderDTO domainToDto(DataDecoder domain);

    SensorTypeId dtoToDomain(SensorTypeIdDTO dto);

    SensorTypeIdDTO domainToDto(SensorTypeId domain);

}
