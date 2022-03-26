package sharespot.services.dataprocessormaster.infrastructure.persistence.postgres.mapper;

import pt.sharespot.iot.core.sensor.properties.KnownPropertyTransformation;
import pt.sharespot.iot.core.sensor.properties.PropertyName;
import pt.sharespot.iot.core.sensor.properties.PropertyTransformation;
import pt.sharespot.iot.core.sensor.properties.PropertyTransformations;
import sharespot.services.dataprocessormaster.domain.DataTransformation;
import sharespot.services.dataprocessormaster.domain.SensorTypeId;
import sharespot.services.dataprocessormaster.infrastructure.persistence.postgres.model.DataTransformationPostgres;
import sharespot.services.dataprocessormaster.infrastructure.persistence.postgres.model.PropertyNamePostgres;
import sharespot.services.dataprocessormaster.infrastructure.persistence.postgres.model.PropertyTransformationPostgres;

import java.util.stream.Collectors;

public class DataTransformationMapper {

    public static DataTransformation postgresToDomain(DataTransformationPostgres postgres) {
        var device = SensorTypeId.of(postgres.deviceType);
        var properties = postgres.entries
                .stream()
                .map(p -> PropertyTransformation.create(p.oldPath, postgresToDomain(p.name)))
                .toArray(PropertyTransformation[]::new);
        var transformations = PropertyTransformations.of(properties);
        return new DataTransformation(device, transformations);
    }

    public static DataTransformationPostgres domainToPostgres(DataTransformation domain) {
        var dataTransformationPostgres = new DataTransformationPostgres();
        dataTransformationPostgres.deviceType = domain.getId().getValue();
        dataTransformationPostgres.entries = domain.getTransform()
                .getTransform()
                .stream()
                .map(pt -> {
                    if (pt instanceof KnownPropertyTransformation kpt) {
                        var propertyTransformationPostgres = new PropertyTransformationPostgres();
                        propertyTransformationPostgres.oldPath = kpt.oldPath();
                        propertyTransformationPostgres.name = domainToPostgres(kpt.newPathLiteral());
                        propertyTransformationPostgres.transformation = dataTransformationPostgres;
                        return propertyTransformationPostgres;
                    } else {
                        throw new IllegalStateException("Unexpected value: " + pt);
                    }
                }).collect(Collectors.toSet());
        return dataTransformationPostgres;
    }

    private static PropertyNamePostgres domainToPostgres(PropertyName domain) {
        return switch (domain) {
            case DATA_ID -> PropertyNamePostgres.dataId();
            case DEVICE_ID -> PropertyNamePostgres.deviceId();
            case DEVICE_NAME -> PropertyNamePostgres.deviceName();
            case REPORTED_AT -> PropertyNamePostgres.reportedAt();
            case LATITUDE -> PropertyNamePostgres.latitude();
            case LONGITUDE -> PropertyNamePostgres.longitude();
            case TEMPERATURE -> PropertyNamePostgres.temperature();
            case MOTION -> PropertyNamePostgres.motion();
            case VELOCITY -> PropertyNamePostgres.velocity();
            case AQI -> PropertyNamePostgres.aqi();
            case HUMIDITY -> PropertyNamePostgres.humidity();
            case PRESSURE -> PropertyNamePostgres.pressure();
            case ILLUMINANCE -> PropertyNamePostgres.illuminance();
            case ALTITUDE -> PropertyNamePostgres.altitude();
            case SOIL_MOISTURE -> PropertyNamePostgres.soilMoisture();
            case BATTERY_PERCENTAGE -> PropertyNamePostgres.batteryPercentage();
            case BATTERY_VOLTS -> PropertyNamePostgres.batteryVolts();
            case ALARM -> PropertyNamePostgres.alarm();
            case READ_PERMISSIONS, READ_WRITE_PERMISSIONS, DEVICE_RECORDS -> throw new RuntimeException();
        };
    }

    private static PropertyName postgresToDomain(PropertyNamePostgres postgres) {
        return switch (postgres.value) {
            case 0 -> PropertyName.DATA_ID;
            case 1 -> PropertyName.DEVICE_ID;
            case 2 -> PropertyName.DEVICE_NAME;
            case 4 -> PropertyName.REPORTED_AT;
            case 5 -> PropertyName.LATITUDE;
            case 6 -> PropertyName.LONGITUDE;
            case 7 -> PropertyName.TEMPERATURE;
            case 8 -> PropertyName.MOTION;
            case 9 -> PropertyName.VELOCITY;
            case 10 -> PropertyName.AQI;
            case 11 -> PropertyName.HUMIDITY;
            case 12 -> PropertyName.PRESSURE;
            case 13 -> PropertyName.SOIL_MOISTURE;
            case 14 -> PropertyName.ILLUMINANCE;
            case 15 -> PropertyName.ALTITUDE;
            case 16 -> PropertyName.BATTERY_PERCENTAGE;
            case 17 -> PropertyName.BATTERY_VOLTS;
            case 18 -> PropertyName.ALARM;
            default -> throw new IllegalStateException("Unexpected value: " + postgres.value);
        };
    }
}
