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

    private static PropertyName postgresToDomain(PropertyNamePostgres postgres) {
        return switch (postgres.value) {
            case 0 -> PropertyName.DATA_ID;
            case 1 -> PropertyName.DEVICE_ID;
            case 2 -> PropertyName.DEVICE_NAME;
            case 3 -> PropertyName.DEVICE_RECORDS;
            case 4 -> PropertyName.REPORTED_AT;
            case 5 -> PropertyName.LATITUDE;
            case 6 -> PropertyName.LONGITUDE;
            case 7 -> PropertyName.TEMPERATURE;
            case 8 -> PropertyName.MOTION;
            default -> throw new IllegalStateException("Unexpected value: " + postgres.value);
        };
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
            case DEVICE_RECORDS -> PropertyNamePostgres.deviceRecords();
            case REPORTED_AT -> PropertyNamePostgres.reportedAt();
            case LATITUDE -> PropertyNamePostgres.latitude();
            case LONGITUDE -> PropertyNamePostgres.longitude();
            case TEMPERATURE -> PropertyNamePostgres.temperature();
            case MOTION -> PropertyNamePostgres.motion();
        };
    }
}
