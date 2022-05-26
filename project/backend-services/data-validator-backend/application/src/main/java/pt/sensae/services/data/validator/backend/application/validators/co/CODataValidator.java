package pt.sensae.services.data.validator.backend.application.validators.co;

import pt.sensae.services.data.validator.backend.application.validators.DataValidator;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.model.properties.PropertyName;
import pt.sharespot.iot.core.sensor.routing.keys.DataLegitimacyOptions;

public class CODataValidator implements DataValidator {
    @Override
    public DataLegitimacyOptions validate(SensorDataDTO data) {
        if (data.hasProperty(PropertyName.CO)) {
            if (data.getSensorData().co.ppm < 0) {
                return DataLegitimacyOptions.INCORRECT;
            }
        }
        return DataLegitimacyOptions.CORRECT;
    }
}
