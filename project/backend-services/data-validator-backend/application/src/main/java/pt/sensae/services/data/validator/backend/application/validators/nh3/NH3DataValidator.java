package pt.sensae.services.data.validator.backend.application.validators.nh3;

import pt.sensae.services.data.validator.backend.application.validators.DataValidator;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.model.properties.PropertyName;
import pt.sharespot.iot.core.sensor.routing.keys.DataLegitimacyOptions;

public class NH3DataValidator implements DataValidator {
    @Override
    public DataLegitimacyOptions validate(SensorDataDTO data) {
        if (data.hasProperty(PropertyName.NH3)) {
            if (data.getSensorData().nh3.ppm < 0) {
                return DataLegitimacyOptions.INCORRECT;
            }
        }
        return DataLegitimacyOptions.CORRECT;
    }
}
