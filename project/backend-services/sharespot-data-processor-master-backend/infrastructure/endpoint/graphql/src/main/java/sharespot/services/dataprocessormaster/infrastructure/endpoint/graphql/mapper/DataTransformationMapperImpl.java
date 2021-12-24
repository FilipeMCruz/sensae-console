package sharespot.services.dataprocessormaster.infrastructure.endpoint.graphql.mapper;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.properties.KnownPropertyTransformation;
import pt.sharespot.iot.core.sensor.properties.PropertyName;
import pt.sharespot.iot.core.sensor.properties.PropertyTransformation;
import pt.sharespot.iot.core.sensor.properties.PropertyTransformations;
import sharespot.services.dataprocessormaster.domain.DataTransformation;
import sharespot.services.dataprocessormaster.domain.SensorTypeId;
import sharespot.services.dataprocessormaster.infrastructure.endpoint.graphql.model.SensorTypeIdDTOImpl;
import sharespot.services.dataprocessormaster.application.DataTransformationDTO;
import sharespot.services.dataprocessormaster.application.DataTransformationMapper;
import sharespot.services.dataprocessormaster.application.SensorTypeIdDTO;
import sharespot.services.dataprocessormaster.infrastructure.endpoint.graphql.model.DataTransformationDTOImpl;
import sharespot.services.dataprocessormaster.infrastructure.endpoint.graphql.model.PropertyNameDTOImpl;
import sharespot.services.dataprocessormaster.infrastructure.endpoint.graphql.model.PropertyTransformationDTOImpl;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DataTransformationMapperImpl implements DataTransformationMapper {

    @Override
    public DataTransformation dtoToDomain(DataTransformationDTO dto) {
        var dataTransformationDTO = (DataTransformationDTOImpl) dto;

        var properties = dataTransformationDTO.entries.stream().map(e -> switch (e.newPath) {
            case DEVICE_ID -> PropertyTransformation.create(e.oldPath, PropertyName.DEVICE_ID);
            case DEVICE_NAME -> PropertyTransformation.create(e.oldPath, PropertyName.DEVICE_NAME);
            case DATA_ID -> PropertyTransformation.create(e.oldPath, PropertyName.DATA_ID);
            case DEVICE_RECORDS -> PropertyTransformation.create(e.oldPath, PropertyName.DEVICE_RECORDS);
            case REPORTED_AT -> PropertyTransformation.create(e.oldPath, PropertyName.REPORTED_AT);
            case LATITUDE -> PropertyTransformation.create(e.oldPath, PropertyName.LATITUDE);
            case LONGITUDE -> PropertyTransformation.create(e.oldPath, PropertyName.LONGITUDE);
            case TEMPERATURE -> PropertyTransformation.create(e.oldPath, PropertyName.TEMPERATURE);
        }).toArray(PropertyTransformation[]::new);

        var hasDuplicateTransformations = Arrays.stream(properties)
                .map(PropertyTransformation::newPath)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .values()
                .stream()
                .anyMatch(occurrences -> occurrences != 1);

        if (hasDuplicateTransformations) {
            throw new RuntimeException("A data transformation can't have two equal identifiers");
        }

        return new DataTransformation(dtoToDomain(dataTransformationDTO.data), PropertyTransformations.of(properties));
    }

    @Override
    public DataTransformationDTO domainToDto(DataTransformation domain) {
        var dto = new DataTransformationDTOImpl();
        var typeDto = new SensorTypeIdDTOImpl();
        typeDto.type = domain.getId().getValue();
        dto.data = typeDto;
        dto.entries = domain.getTransform().getTransform().stream().map(e -> {
            if (e instanceof KnownPropertyTransformation kt) {
                return domainToDto(kt);
            } else {
                throw new IllegalStateException("Unexpected value: " + e);
            }
        }).collect(Collectors.toSet());
        return dto;
    }

    private PropertyTransformationDTOImpl domainToDto(KnownPropertyTransformation kt) {
        var entry = new PropertyTransformationDTOImpl();
        entry.newPath = switch (kt.newPathLiteral()) {
            case DATA_ID -> PropertyNameDTOImpl.DATA_ID;
            case DEVICE_ID -> PropertyNameDTOImpl.DEVICE_ID;
            case DEVICE_NAME -> PropertyNameDTOImpl.DEVICE_NAME;
            case DEVICE_RECORDS -> PropertyNameDTOImpl.DEVICE_RECORDS;
            case REPORTED_AT -> PropertyNameDTOImpl.REPORTED_AT;
            case LATITUDE -> PropertyNameDTOImpl.LATITUDE;
            case LONGITUDE -> PropertyNameDTOImpl.LONGITUDE;
            case TEMPERATURE -> PropertyNameDTOImpl.TEMPERATURE;
        };
        entry.oldPath = kt.oldPath();
        return entry;
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
