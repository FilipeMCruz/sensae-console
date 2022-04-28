package pt.sensae.services.data.validator.backend.application.validators.nh3;

import pt.sensae.services.data.validator.backend.application.validators.DataValidator;
import pt.sharespot.iot.core.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.properties.PropertyName;

public class NH3DataValidator implements DataValidator {
    @Override
    public DataLegitimacyOptions validate(ProcessedSensorDataDTO data) {
        if (data.hasProperty(PropertyName.NH3)) {
            if (data.getSensorData().nh3.ppm < 0) {
                return DataLegitimacyOptions.INCORRECT;
            }
        }
        return DataLegitimacyOptions.CORRECT;
    }
}
