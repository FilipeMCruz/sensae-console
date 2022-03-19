package sharespot.services.datadecodermaster.infrastructure.endpoint.graphql.mapper;

import org.springframework.stereotype.Service;
import sharespot.services.datadecodermaster.infrastructure.endpoint.graphql.model.DataDecoderDTOImpl;
import sharespot.services.datadecodermaster.infrastructure.endpoint.graphql.model.SensorTypeIdDTOImpl;
import sharespot.services.datadecodermaster.application.DataDecoderDTO;
import sharespot.services.datadecodermaster.application.DataDecoderMapper;
import sharespot.services.datadecodermaster.application.SensorTypeIdDTO;
import sharespot.services.datadecodermaster.domain.DataDecoder;
import sharespot.services.datadecodermaster.domain.SensorTypeId;
import sharespot.services.datadecodermaster.domain.SensorTypeScript;

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
        typeDto.type = domain.getId().getValue();
        dto.data = typeDto;
        dto.script = domain.getScript().getValue();
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
