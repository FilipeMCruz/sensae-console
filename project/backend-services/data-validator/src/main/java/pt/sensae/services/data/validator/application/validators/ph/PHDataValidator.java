package pt.sensae.services.data.validator.application.validators.ph;

import pt.sensae.services.data.validator.application.validators.DataValidator;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.model.properties.PropertyName;
import pt.sharespot.iot.core.sensor.routing.keys.DataLegitimacyOptions;

public class PHDataValidator implements DataValidator {

    @Override
    public DataLegitimacyOptions validate(SensorDataDTO data) {
        if (data.hasProperty(PropertyName.PH)) {
            if (data.getSensorData().ph.value < 0 || data.getSensorData().ph.value > 14) {
                return DataLegitimacyOptions.INCORRECT;
            }
        }

        return DataLegitimacyOptions.CORRECT;
    }
}
