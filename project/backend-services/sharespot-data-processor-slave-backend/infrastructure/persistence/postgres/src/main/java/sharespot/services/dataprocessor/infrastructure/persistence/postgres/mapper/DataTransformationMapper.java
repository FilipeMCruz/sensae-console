package sharespot.services.dataprocessor.infrastructure.persistence.postgres.mapper;

import pt.sharespot.iot.core.sensor.properties.PropertyName;
import pt.sharespot.iot.core.sensor.properties.PropertyTransformation;
import pt.sharespot.iot.core.sensor.properties.PropertyTransformations;
import sharespot.services.dataprocessor.domain.DataTransformation;
import sharespot.services.dataprocessor.domain.SensorTypeId;
import sharespot.services.dataprocessor.infrastructure.persistence.postgres.model.DataTransformationPostgres;
import sharespot.services.dataprocessor.infrastructure.persistence.postgres.model.PropertyNamePostgres;

public class DataTransformationMapper {

    public static DataTransformation postgresToDomain(DataTransformationPostgres postgres) {
        var device = SensorTypeId.of(postgres.deviceType);
        var properties = postgres.entries
                .stream()
                .map(p -> PropertyTransformation.create(p.oldPath, postgresToDomain(p.name), p.subSensorId))
                .toArray(PropertyTransformation[]::new);
        var transformations = PropertyTransformations.of(properties);
        return new DataTransformation(device, transformations);
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
            case 11 -> PropertyName.AIR_HUMIDITY_GRAMS_PER_CUBIC_METER;
            case 12 -> PropertyName.AIR_PRESSURE;
            case 13 -> PropertyName.SOIL_MOISTURE;
            case 14 -> PropertyName.ILLUMINANCE;
            case 15 -> PropertyName.ALTITUDE;
            case 16 -> PropertyName.BATTERY_PERCENTAGE;
            case 17 -> PropertyName.BATTERY_VOLTS;
            case 18 -> PropertyName.TRIGGER;
            case 19 -> PropertyName.AIR_HUMIDITY_RELATIVE_PERCENTAGE;
            case 20 -> PropertyName.WATER_PRESSURE;
            case 21 -> PropertyName.BATTERY_MIN_VOLTS;
            case 22 -> PropertyName.BATTERY_MAX_VOLTS;
            case 23 -> PropertyName.PH;
            case 24 -> PropertyName.DISTANCE;
            case 25 -> PropertyName.MIN_DISTANCE;
            case 26 -> PropertyName.MAX_DISTANCE;
            case 27 -> PropertyName.OCCUPATION;
            case 28 -> PropertyName.SOIL_CONDUCTIVITY;
            case 29 -> PropertyName.CO2;
            case 30 -> PropertyName.CO;
            case 31 -> PropertyName.NH3;
            case 32 -> PropertyName.O3;
            case 33 -> PropertyName.NO2;
            case 34 -> PropertyName.VOC;
            case 35 -> PropertyName.PM2_5;
            case 36 -> PropertyName.PM10;
            default -> throw new IllegalStateException("Unexpected value: " + postgres.value);
        };
    }
}
