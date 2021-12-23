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
            default -> throw new IllegalStateException("Unexpected value: " + postgres.value);
        };
    }
}
