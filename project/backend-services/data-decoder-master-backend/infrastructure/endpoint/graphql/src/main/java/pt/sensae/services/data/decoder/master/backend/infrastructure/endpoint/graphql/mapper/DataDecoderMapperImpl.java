package pt.sensae.services.data.decoder.master.backend.infrastructure.endpoint.graphql.mapper;

import org.springframework.stereotype.Service;
import pt.sensae.services.data.decoder.master.backend.infrastructure.endpoint.graphql.model.DataDecoderDTOImpl;
import pt.sensae.services.data.decoder.master.backend.infrastructure.endpoint.graphql.model.SensorTypeIdDTOImpl;
import pt.sensae.services.data.decoder.master.backend.application.DataDecoderDTO;
import pt.sensae.services.data.decoder.master.backend.application.DataDecoderMapper;
import pt.sensae.services.data.decoder.master.backend.application.SensorTypeIdDTO;
import pt.sensae.services.data.decoder.master.backend.domain.DataDecoder;
import pt.sensae.services.data.decoder.master.backend.domain.SensorTypeId;
import pt.sensae.services.data.decoder.master.backend.domain.SensorTypeScript;

@Service
public class DataDecoderMapperImpl implements DataDecoderMapper {

    @Override
    public DataDecoder dtoToDomain(DataDecoderDTO dto) {
        var dataTransformationDTO = (DataDecoderDTOImpl) dto;
        return new DataDecoder(dtoToDomain(dataTransformationDTO.data), SensorTypeScript.of(dataTransformationDTO.script));
    }

    @Override
    public DataDecoderDTO domainToDto(DataDecoder domain, Long instant) {
        var dto = new DataDecoderDTOImpl();
        var typeDto = new SensorTypeIdDTOImpl();
        typeDto.type = domain.id().getValue();
        dto.data = typeDto;
        dto.script = domain.script().value();
        dto.lastTimeSeen = instant.toString();
        return dto;
    }

    @Override
    public SensorTypeId dtoToDomain(SensorTypeIdDTO dto) {
        var typeDto = (SensorTypeIdDTOImpl) dto;
        return SensorTypeId.of(typeDto.type);
    }

    @Override
    public SensorTypeIdDTO domainToDto(SensorTypeId domain) {
        var typeDto = new SensorTypeIdDTOImpl();
        typeDto.type = domain.getValue();
        return typeDto;
    }
}
