package sharespot.services.dataprocessormaster.infrastructure.endpoint.amqp.mapper;

import org.springframework.stereotype.Component;
import pt.sharespot.iot.core.sensor.model.properties.KnownPropertyTransformation;
import sharespot.services.dataprocessormaster.application.DataTransformationEventMapper;
import sharespot.services.dataprocessormaster.application.DataTransformationNotificationDTO;
import sharespot.services.dataprocessormaster.application.SensorTypeIdDTO;
import sharespot.services.dataprocessormaster.domain.DataTransformation;
import sharespot.services.dataprocessormaster.domain.SensorTypeId;
import sharespot.services.dataprocessormaster.infrastructure.endpoint.amqp.model.*;

import java.util.stream.Collectors;

@Component
public class DataTransformationEventMapperImpl implements DataTransformationEventMapper {

    @Override
    public DataTransformationNotificationDTO domainToUpdatedDto(DataTransformation domain) {
        var dto = new DataTransformationNotificationDTOImpl();
        dto.type = DataTransformationNotificationTypeDTOImpl.UPDATE;
        dto.sensorType = domain.getId().getValue();
        var info = new DataTransformationDTOImpl();
        info.entries = domain.getTransform().getTransform().stream().map(e -> {
            if (e instanceof KnownPropertyTransformation kt) {
                return domainToDto(kt);
            } else {
                throw new IllegalStateException("Unexpected value: " + e);
            }
        }).collect(Collectors.toSet());
        dto.information = info;
        return dto;
    }

    @Override
    public DataTransformationNotificationDTO domainToDeletedDto(SensorTypeId domain) {
        var dto = new DataTransformationNotificationDTOImpl();
        dto.type = DataTransformationNotificationTypeDTOImpl.DELETE;
        dto.sensorType = domain.getValue();
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
        return SensorTypeId.of(typeDto.sensorType);
    }
}
