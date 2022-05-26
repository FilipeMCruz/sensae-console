package pt.sensae.services.data.validator.backend.application.validators.pm25;

import pt.sensae.services.data.validator.backend.application.validators.DataValidator;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.model.properties.PropertyName;
import pt.sharespot.iot.core.sensor.routing.keys.DataLegitimacyOptions;

public class PM2_5DataValidator implements DataValidator {

    @Override
    public DataLegitimacyOptions validate(SensorDataDTO data) {
        if (data.hasProperty(PropertyName.PM2_5)) {
            if (data.getSensorData().pm2_5.microGramsPerCubicMeter < 0 || data.getSensorData().pm2_5.microGramsPerCubicMeter > 600) {
                return DataLegitimacyOptions.INCORRECT;
            }
        }

        return DataLegitimacyOptions.CORRECT;
    }
}
