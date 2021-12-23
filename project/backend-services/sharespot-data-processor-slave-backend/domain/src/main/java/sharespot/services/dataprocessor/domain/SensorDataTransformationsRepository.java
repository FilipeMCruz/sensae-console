package sharespot.services.dataprocessor.domain;

import java.util.Optional;

public interface SensorDataTransformationsRepository {

//    private void init() {
//        var lgt92propertyTransformations = PropertyTransformations.of(
//                PropertyTransformation.create("decoded.payload.Latitude", PropertyName.LATITUDE),
//                PropertyTransformation.create("decoded.payload.Longitude", PropertyName.LONGITUDE),
//                PropertyTransformation.create("uuid", PropertyName.DATA_ID),
//                PropertyTransformation.create("id", PropertyName.DEVICE_ID),
//                PropertyTransformation.create("name", PropertyName.DEVICE_NAME),
//                PropertyTransformation.create("reported_at", PropertyName.REPORTED_AT)
//        );
//        transformations.add(new DataTransformation(SensorTypeId.of("lgt92"), lgt92propertyTransformations));
//    }

    Optional<DataTransformation> findByDeviceId(SensorTypeId id);
}
