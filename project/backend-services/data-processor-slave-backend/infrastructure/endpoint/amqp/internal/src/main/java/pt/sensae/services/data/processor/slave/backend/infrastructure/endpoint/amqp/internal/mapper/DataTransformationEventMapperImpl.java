package pt.sensae.services.data.processor.slave.backend.infrastructure.endpoint.amqp.internal.mapper;

import org.springframework.stereotype.Component;
import pt.sensae.services.data.processor.slave.backend.infrastructure.endpoint.amqp.internal.model.DataTransformationNotificationDTOImpl;
import pt.sensae.services.data.processor.slave.backend.infrastructure.endpoint.amqp.internal.model.DataTransformationNotificationTypeDTOImpl;
import pt.sensae.services.data.processor.slave.backend.infrastructure.endpoint.amqp.internal.model.SensorTypeIdDTOImpl;
import pt.sharespot.iot.core.sensor.model.properties.PropertyName;
import pt.sharespot.iot.core.sensor.model.properties.PropertyTransformation;
import pt.sharespot.iot.core.sensor.model.properties.PropertyTransformations;
import pt.sensae.services.data.processor.slave.backend.application.DataTransformationEventMapper;
import pt.sensae.services.data.processor.slave.backend.application.DataTransformationNotificationDTO;
import pt.sensae.services.data.processor.slave.backend.application.SensorTypeIdDTO;
import pt.sensae.services.data.processor.slave.backend.domain.DataTransformation;
import pt.sensae.services.data.processor.slave.backend.domain.DataTransformationNotification;
import pt.sensae.services.data.processor.slave.backend.domain.NotificationType;
import pt.sensae.services.data.processor.slave.backend.domain.SensorTypeId;

@Component
public class DataTransformationEventMapperImpl implements DataTransformationEventMapper {

    @Override
    public DataTransformationNotification dtoToDomain(DataTransformationNotificationDTO dto) {
        var notificationDTO = (DataTransformationNotificationDTOImpl) dto;
        var id = dtoToDomain(notificationDTO.sensorType);
        var dataTransformationDTO = notificationDTO.information;
        if (notificationDTO.type.equals(DataTransformationNotificationTypeDTOImpl.UPDATE)) {
            var properties = dataTransformationDTO.entries.stream()
                    .filter(kt -> kt.oldPath != null && !kt.oldPath.isBlank() && kt.sensorID != null)
                    .map(e -> switch (e.newPath) {
                        case DEVICE_ID -> PropertyTransformation.create(e.oldPath, PropertyName.DEVICE_ID, e.sensorID);
                        case DEVICE_NAME ->
                                PropertyTransformation.create(e.oldPath, PropertyName.DEVICE_NAME, e.sensorID);
                        case DATA_ID -> PropertyTransformation.create(e.oldPath, PropertyName.DATA_ID, e.sensorID);
                        case REPORTED_AT ->
                                PropertyTransformation.create(e.oldPath, PropertyName.REPORTED_AT, e.sensorID);
                        case LATITUDE -> PropertyTransformation.create(e.oldPath, PropertyName.LATITUDE, e.sensorID);
                        case LONGITUDE -> PropertyTransformation.create(e.oldPath, PropertyName.LONGITUDE, e.sensorID);
                        case TEMPERATURE ->
                                PropertyTransformation.create(e.oldPath, PropertyName.TEMPERATURE, e.sensorID);
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
                        case ILLUMINANCE ->
                                PropertyTransformation.create(e.oldPath, PropertyName.ILLUMINANCE, e.sensorID);
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
                        case OCCUPATION ->
                                PropertyTransformation.create(e.oldPath, PropertyName.OCCUPATION, e.sensorID);
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
            var info = new DataTransformation(id, PropertyTransformations.of(properties));
            return new DataTransformationNotification(id, NotificationType.UPDATE, info);
        } else {
            return new DataTransformationNotification(id, NotificationType.DELETE, null);
        }
    }

    public SensorTypeId dtoToDomain(String dto) {
        return SensorTypeId.of(dto);
    }

    @Override
    public SensorTypeIdDTO domainToDto(SensorTypeId domain) {
        var typeDto = new SensorTypeIdDTOImpl();
        typeDto.sensorType = domain.getValue();
        return typeDto;
    }
}
