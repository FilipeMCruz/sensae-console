package sharespot.services.dataprocessormaster.infrastructure.persistence.postgres.mapper;

import pt.sharespot.iot.core.sensor.model.properties.KnownPropertyTransformation;
import pt.sharespot.iot.core.sensor.model.properties.PropertyName;
import pt.sharespot.iot.core.sensor.model.properties.PropertyTransformation;
import pt.sharespot.iot.core.sensor.model.properties.PropertyTransformations;
import sharespot.services.dataprocessormaster.domain.DataTransformation;
import sharespot.services.dataprocessormaster.domain.SensorTypeId;
import sharespot.services.dataprocessormaster.infrastructure.persistence.postgres.model.DataTransformationPostgres;
import sharespot.services.dataprocessormaster.infrastructure.persistence.postgres.model.PropertyNamePostgres;
import sharespot.services.dataprocessormaster.infrastructure.persistence.postgres.model.PropertyTransformationPostgres;

import java.util.stream.Collectors;

public class DataTransformationMapper {

    public static DataTransformation postgresToDomain(DataTransformationPostgres postgres) {
        var device = SensorTypeId.of(postgres.deviceType);
        var properties = postgres.entries.stream()
                .map(p -> PropertyTransformation.create(p.oldPath, postgresToDomain(p.name), p.subSensorId))
                .toArray(PropertyTransformation[]::new);
        var transformations = PropertyTransformations.of(properties);
        return new DataTransformation(device, transformations);
    }

    public static DataTransformationPostgres domainToPostgres(DataTransformation domain) {
        var dataTransformationPostgres = new DataTransformationPostgres();
        dataTransformationPostgres.deviceType = domain.getId().getValue();
        dataTransformationPostgres.entries = domain.getTransform().getTransform().stream().map(pt -> {
            if (pt instanceof KnownPropertyTransformation kpt) {
                var propertyTransformationPostgres = new PropertyTransformationPostgres();
                propertyTransformationPostgres.oldPath = kpt.oldPath();
                propertyTransformationPostgres.subSensorId = kpt.subSensorId();
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
            case DATA_ID -> PropertyNamePostgres.of(0);
            case DEVICE_ID -> PropertyNamePostgres.of(1);
            case DEVICE_NAME -> PropertyNamePostgres.of(2);
            case REPORTED_AT -> PropertyNamePostgres.of(4);
            case LATITUDE -> PropertyNamePostgres.of(5);
            case LONGITUDE -> PropertyNamePostgres.of(6);
            case TEMPERATURE -> PropertyNamePostgres.of(7);
            case MOTION -> PropertyNamePostgres.of(8);
            case VELOCITY -> PropertyNamePostgres.of(9);
            case AQI -> PropertyNamePostgres.of(10);
            case AIR_HUMIDITY_GRAMS_PER_CUBIC_METER -> PropertyNamePostgres.of(11);
            case AIR_HUMIDITY_RELATIVE_PERCENTAGE -> PropertyNamePostgres.of(19);
            case AIR_PRESSURE -> PropertyNamePostgres.of(12);
            case ILLUMINANCE -> PropertyNamePostgres.of(14);
            case ALTITUDE -> PropertyNamePostgres.of(15);
            case SOIL_MOISTURE -> PropertyNamePostgres.of(13);
            case WATER_PRESSURE -> PropertyNamePostgres.of(20);
            case BATTERY_PERCENTAGE -> PropertyNamePostgres.of(16);
            case BATTERY_VOLTS -> PropertyNamePostgres.of(17);
            case TRIGGER -> PropertyNamePostgres.of(18);
            case BATTERY_MIN_VOLTS -> PropertyNamePostgres.of(21);
            case BATTERY_MAX_VOLTS -> PropertyNamePostgres.of(22);
            case PH -> PropertyNamePostgres.of(23);
            case DISTANCE -> PropertyNamePostgres.of(24);
            case MIN_DISTANCE -> PropertyNamePostgres.of(25);
            case MAX_DISTANCE -> PropertyNamePostgres.of(26);
            case OCCUPATION -> PropertyNamePostgres.of(27);
            case SOIL_CONDUCTIVITY -> PropertyNamePostgres.of(28);
            case CO2 -> PropertyNamePostgres.of(29);
            case CO -> PropertyNamePostgres.of(30);
            case NH3 -> PropertyNamePostgres.of(31);
            case O3 -> PropertyNamePostgres.of(32);
            case NO2 -> PropertyNamePostgres.of(33);
            case VOC -> PropertyNamePostgres.of(34);
            case PM2_5 -> PropertyNamePostgres.of(35);
            case PM10 -> PropertyNamePostgres.of(36);
            case DEVICE_DOWNLINK -> PropertyNamePostgres.of(37);
            case READ_PERMISSIONS, READ_WRITE_PERMISSIONS, DEVICE_RECORDS, DEVICE_COMMANDS ->
                    throw new RuntimeException();
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
            case 37 -> PropertyName.DEVICE_DOWNLINK;
            default -> throw new IllegalStateException("Unexpected value: " + postgres.value);
        };
    }
}
