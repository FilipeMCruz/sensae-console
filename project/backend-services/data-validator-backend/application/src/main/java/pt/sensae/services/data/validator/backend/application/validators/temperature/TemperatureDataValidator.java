package pt.sensae.services.data.validator.backend.application.validators.temperature;

import pt.sensae.services.data.validator.backend.application.validators.DataValidator;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.model.properties.PropertyName;
import pt.sharespot.iot.core.sensor.routing.keys.DataLegitimacyOptions;

public class TemperatureDataValidator implements DataValidator {

    @Override
    public DataLegitimacyOptions validate(SensorDataDTO data) {
        if (data.hasProperty(PropertyName.TEMPERATURE)) {
            if (data.getSensorData().temperature.celsius < -10 || data.getSensorData().temperature.celsius > 60) {
                return DataLegitimacyOptions.INCORRECT;
            }
        }

        return DataLegitimacyOptions.CORRECT;
    }
}
