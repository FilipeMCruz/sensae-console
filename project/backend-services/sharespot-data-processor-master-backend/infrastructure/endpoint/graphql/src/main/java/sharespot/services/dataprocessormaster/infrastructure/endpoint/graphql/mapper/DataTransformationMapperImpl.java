package sharespot.services.dataprocessormaster.infrastructure.endpoint.graphql.mapper;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.model.properties.KnownPropertyTransformation;
import pt.sharespot.iot.core.sensor.model.properties.PropertyName;
import pt.sharespot.iot.core.sensor.model.properties.PropertyTransformation;
import pt.sharespot.iot.core.sensor.model.properties.PropertyTransformations;
import sharespot.services.dataprocessormaster.application.DataTransformationDTO;
import sharespot.services.dataprocessormaster.application.DataTransformationMapper;
import sharespot.services.dataprocessormaster.application.SensorTypeIdDTO;
import sharespot.services.dataprocessormaster.domain.DataTransformation;
import sharespot.services.dataprocessormaster.domain.SensorTypeId;
import sharespot.services.dataprocessormaster.infrastructure.endpoint.graphql.model.DataTransformationDTOImpl;
import sharespot.services.dataprocessormaster.infrastructure.endpoint.graphql.model.PropertyNameDTOImpl;
import sharespot.services.dataprocessormaster.infrastructure.endpoint.graphql.model.PropertyTransformationDTOImpl;
import sharespot.services.dataprocessormaster.infrastructure.endpoint.graphql.model.SensorTypeIdDTOImpl;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DataTransformationMapperImpl implements DataTransformationMapper {

    @Override
    public DataTransformation dtoToDomain(DataTransformationDTO dto) {
        var dataTransformationDTO = (DataTransformationDTOImpl) dto;

        var properties = dataTransformationDTO.entries.stream()
                .filter(kt -> kt.oldPath != null && !kt.oldPath.isBlank() && kt.sensorID != null)
                .map(e -> switch (e.newPath) {
                    case DEVICE_ID -> PropertyTransformation.create(e.oldPath, PropertyName.DEVICE_ID, e.sensorID);
                    case DEVICE_NAME -> PropertyTransformation.create(e.oldPath, PropertyName.DEVICE_NAME, e.sensorID);
                    case DATA_ID -> PropertyTransformation.create(e.oldPath, PropertyName.DATA_ID, e.sensorID);
                    case REPORTED_AT -> PropertyTransformation.create(e.oldPath, PropertyName.REPORTED_AT, e.sensorID);
                    case LATITUDE -> PropertyTransformation.create(e.oldPath, PropertyName.LATITUDE, e.sensorID);
                    case LONGITUDE -> PropertyTransformation.create(e.oldPath, PropertyName.LONGITUDE, e.sensorID);
                    case TEMPERATURE -> PropertyTransformation.create(e.oldPath, PropertyName.TEMPERATURE, e.sensorID);
                    case MOTION -> PropertyTransformation.create(e.oldPath, PropertyName.MOTION, e.sensorID);
                    case VELOCITY -> PropertyTransformation.create(e.oldPath, PropertyName.VELOCITY, e.sensorID);
                    case AQI -> PropertyTransformation.create(e.oldPath, PropertyName.AQI, e.sensorID);
                    case AIR_HUMIDITY_GRAMS_PER_CUBIC_METER ->
                            PropertyTransformation.create(e.oldPath, PropertyName.AIR_HUMIDITY_GRAMS_PER_CUBIC_METER, e.sensorID);
                    case AIR_HUMIDITY_RELATIVE_PERCENTAGE ->
                            PropertyTransformation.create(e.oldPath, PropertyName.AIR_HUMIDITY_RELATIVE_PERCENTAGE, e.sensorID);
                    case AIR_PRESSURE ->
                            PropertyTransformation.create(e.oldPath, PropertyName.AIR_PRESSURE, e.sensorID);
                    case SOIL_MOISTURE ->
                            PropertyTransformation.create(e.oldPath, PropertyName.SOIL_MOISTURE, e.sensorID);
                    case ILLUMINANCE -> PropertyTransformation.create(e.oldPath, PropertyName.ILLUMINANCE, e.sensorID);
                    case ALTITUDE -> PropertyTransformation.create(e.oldPath, PropertyName.ALTITUDE, e.sensorID);
                    case BATTERY_VOLTS ->
                            PropertyTransformation.create(e.oldPath, PropertyName.BATTERY_VOLTS, e.sensorID);
                    case BATTERY_MAX_VOLTS ->
                            PropertyTransformation.create(e.oldPath, PropertyName.BATTERY_MAX_VOLTS, e.sensorID);
                    case BATTERY_MIN_VOLTS ->
                            PropertyTransformation.create(e.oldPath, PropertyName.BATTERY_MIN_VOLTS, e.sensorID);
                    case BATTERY_PERCENTAGE ->
                            PropertyTransformation.create(e.oldPath, PropertyName.BATTERY_PERCENTAGE, e.sensorID);
                    case TRIGGER -> PropertyTransformation.create(e.oldPath, PropertyName.TRIGGER, e.sensorID);

                    case CO -> PropertyTransformation.create(e.oldPath, PropertyName.CO, e.sensorID);
                    case CO2 -> PropertyTransformation.create(e.oldPath, PropertyName.CO2, e.sensorID);
                    case DISTANCE -> PropertyTransformation.create(e.oldPath, PropertyName.DISTANCE, e.sensorID);
                    case MAX_DISTANCE ->
                            PropertyTransformation.create(e.oldPath, PropertyName.MAX_DISTANCE, e.sensorID);
                    case MIN_DISTANCE ->
                            PropertyTransformation.create(e.oldPath, PropertyName.MIN_DISTANCE, e.sensorID);
                    case NH3 -> PropertyTransformation.create(e.oldPath, PropertyName.NH3, e.sensorID);
                    case NO2 -> PropertyTransformation.create(e.oldPath, PropertyName.NO2, e.sensorID);
                    case O3 -> PropertyTransformation.create(e.oldPath, PropertyName.O3, e.sensorID);
                    case OCCUPATION -> PropertyTransformation.create(e.oldPath, PropertyName.OCCUPATION, e.sensorID);
                    case PH -> PropertyTransformation.create(e.oldPath, PropertyName.PH, e.sensorID);
                    case PM10 -> PropertyTransformation.create(e.oldPath, PropertyName.PM10, e.sensorID);
                    case PM2_5 -> PropertyTransformation.create(e.oldPath, PropertyName.PM2_5, e.sensorID);
                    case SOIL_CONDUCTIVITY ->
                            PropertyTransformation.create(e.oldPath, PropertyName.SOIL_CONDUCTIVITY, e.sensorID);
                    case VOC -> PropertyTransformation.create(e.oldPath, PropertyName.VOC, e.sensorID);
                    case WATER_PRESSURE ->
                            PropertyTransformation.create(e.oldPath, PropertyName.WATER_PRESSURE, e.sensorID);
                    case DEVICE_DOWNLINK ->
                            PropertyTransformation.create(e.oldPath, PropertyName.DEVICE_DOWNLINK, e.sensorID);
                }).toArray(PropertyTransformation[]::new);

        var hasDuplicateTransformations = Arrays.stream(properties)
                .map(p -> p.newPath() + "-" + p.subSensorId())
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
            case REPORTED_AT -> PropertyNameDTOImpl.REPORTED_AT;
            case LATITUDE -> PropertyNameDTOImpl.LATITUDE;
            case LONGITUDE -> PropertyNameDTOImpl.LONGITUDE;
            case TEMPERATURE -> PropertyNameDTOImpl.TEMPERATURE;
            case MOTION -> PropertyNameDTOImpl.MOTION;
            case VELOCITY -> PropertyNameDTOImpl.VELOCITY;
            case AQI -> PropertyNameDTOImpl.AQI;
            case AIR_HUMIDITY_GRAMS_PER_CUBIC_METER -> PropertyNameDTOImpl.AIR_HUMIDITY_GRAMS_PER_CUBIC_METER;
            case AIR_HUMIDITY_RELATIVE_PERCENTAGE -> PropertyNameDTOImpl.AIR_HUMIDITY_RELATIVE_PERCENTAGE;
            case AIR_PRESSURE -> PropertyNameDTOImpl.AIR_PRESSURE;
            case SOIL_MOISTURE -> PropertyNameDTOImpl.SOIL_MOISTURE;
            case ILLUMINANCE -> PropertyNameDTOImpl.ILLUMINANCE;
            case ALTITUDE -> PropertyNameDTOImpl.ALTITUDE;
            case BATTERY_VOLTS -> PropertyNameDTOImpl.BATTERY_VOLTS;
            case WATER_PRESSURE -> PropertyNameDTOImpl.WATER_PRESSURE;
            case BATTERY_PERCENTAGE -> PropertyNameDTOImpl.BATTERY_PERCENTAGE;
            case TRIGGER -> PropertyNameDTOImpl.TRIGGER;
            case BATTERY_MIN_VOLTS -> PropertyNameDTOImpl.BATTERY_MIN_VOLTS;
            case BATTERY_MAX_VOLTS -> PropertyNameDTOImpl.BATTERY_MAX_VOLTS;
            case PH -> PropertyNameDTOImpl.PH;
            case DISTANCE -> PropertyNameDTOImpl.DISTANCE;
            case MIN_DISTANCE -> PropertyNameDTOImpl.MIN_DISTANCE;
            case MAX_DISTANCE -> PropertyNameDTOImpl.MAX_DISTANCE;
            case OCCUPATION -> PropertyNameDTOImpl.OCCUPATION;
            case SOIL_CONDUCTIVITY -> PropertyNameDTOImpl.SOIL_CONDUCTIVITY;
            case CO2 -> PropertyNameDTOImpl.CO2;
            case CO -> PropertyNameDTOImpl.CO;
            case NH3 -> PropertyNameDTOImpl.NH3;
            case O3 -> PropertyNameDTOImpl.O3;
            case NO2 -> PropertyNameDTOImpl.NO2;
            case VOC -> PropertyNameDTOImpl.VOC;
            case PM2_5 -> PropertyNameDTOImpl.PM2_5;
            case PM10 -> PropertyNameDTOImpl.PM10;
            case DEVICE_DOWNLINK -> PropertyNameDTOImpl.DEVICE_DOWNLINK;
            default -> throw new RuntimeException("Invalid Property");
        };

        entry.oldPath = kt.oldPath();
        entry.sensorID = kt.subSensorId();
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
