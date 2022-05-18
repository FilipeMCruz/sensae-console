package sharespot.services.data.decoder.master.infrastructure.endpoint.graphql.mapper;

import org.springframework.stereotype.Service;
import sharespot.services.data.decoder.master.infrastructure.endpoint.graphql.model.DataDecoderDTOImpl;
import sharespot.services.data.decoder.master.infrastructure.endpoint.graphql.model.SensorTypeIdDTOImpl;
import sharespot.services.data.decoder.master.application.DataDecoderDTO;
import sharespot.services.data.decoder.master.application.DataDecoderMapper;
import sharespot.services.data.decoder.master.application.SensorTypeIdDTO;
import sharespot.services.data.decoder.master.domain.DataDecoder;
import sharespot.services.data.decoder.master.domain.SensorTypeId;
import sharespot.services.data.decoder.master.domain.SensorTypeScript;

@Service
public class DataDecoderMapperImpl implements DataDecoderMapper {

    @Override
    public DataDecoder dtoToDomain(DataDecoderDTO dto) {
        var dataTransformationDTO = (DataDecoderDTOImpl) dto;
        return new DataDecoder(dtoToDomain(dataTransformationDTO.data), SensorTypeScript.of(dataTransformationDTO.script));
    }

    @Override
    public DataDecoderDTO domainToDto(DataDecoder domain) {
        var dto = new DataDecoderDTOImpl();
        var typeDto = new SensorTypeIdDTOImpl();
        typeDto.type = domain.id().getValue();
        dto.data = typeDto;
        dto.script = domain.script().value();
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
