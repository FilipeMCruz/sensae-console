package pt.sensae.services.data.validator.backend.application.validators.velocity;

import pt.sharespot.iot.core.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.properties.PropertyName;
import pt.sensae.services.data.validator.backend.application.validators.DataValidator;

public class VelocityDataValidator implements DataValidator {

    @Override
    public DataLegitimacyOptions validate(ProcessedSensorDataDTO data) {
        if (data.hasProperty(PropertyName.VELOCITY)) {
            if (data.getSensorData().velocity.kilometersPerHour < 0 || data.getSensorData().velocity.kilometersPerHour > 150) {
                return DataLegitimacyOptions.INCORRECT;
            }
        }

        return DataLegitimacyOptions.CORRECT;
    }
}
