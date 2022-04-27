package pt.sensae.services.data.validator.backend.application.validators.distance;

import pt.sensae.services.data.validator.backend.application.validators.DataValidator;
import pt.sharespot.iot.core.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.properties.PropertyName;

public class DistanceDataValidator implements DataValidator {

    @Override
    public DataLegitimacyOptions validate(ProcessedSensorDataDTO data) {
        if (data.hasProperty(PropertyName.MIN_DISTANCE)) {
            if (data.getSensorData().distance.minMillimeters < 0) {
                return DataLegitimacyOptions.INCORRECT;
            }
        }
        if (data.hasAllProperties(PropertyName.MIN_DISTANCE, PropertyName.MAX_DISTANCE)) {
            if (data.getSensorData().distance.maxMillimeters < data.getSensorData().distance.minMillimeters) {
                return DataLegitimacyOptions.INCORRECT;
            }
        }

        if (data.hasProperty(PropertyName.DISTANCE)) {
            if (data.getSensorData().distance.millimeters < 0) {
                return DataLegitimacyOptions.INCORRECT;
            }
            if (data.hasProperty(PropertyName.MAX_DISTANCE)) {
                if (data.getSensorData().distance.maxMillimeters < data.getSensorData().distance.millimeters) {
                    return DataLegitimacyOptions.INCORRECT;
                }
            }
            if (data.hasProperty(PropertyName.MIN_DISTANCE)) {
                if (data.getSensorData().distance.minMillimeters > data.getSensorData().distance.millimeters) {
                    return DataLegitimacyOptions.INCORRECT;
                }
            }
        }
        return DataLegitimacyOptions.CORRECT;
    }
}
