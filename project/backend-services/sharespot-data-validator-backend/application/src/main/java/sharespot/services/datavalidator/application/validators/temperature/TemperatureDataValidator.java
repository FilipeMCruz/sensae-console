package sharespot.services.datavalidator.application.validators.temperature;

import pt.sharespot.iot.core.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.properties.PropertyName;
import sharespot.services.datavalidator.application.validators.DataValidator;

public class TemperatureDataValidator implements DataValidator {

    @Override
    public DataLegitimacyOptions validate(ProcessedSensorDataDTO data) {
        if (data.hasProperty(PropertyName.TEMPERATURE)) {
            if (data.getSensorData().temperature.celsius < -10 || data.getSensorData().temperature.celsius > 60) {
                return DataLegitimacyOptions.INCORRECT;
            }
        }

        return DataLegitimacyOptions.CORRECT;
    }
}
