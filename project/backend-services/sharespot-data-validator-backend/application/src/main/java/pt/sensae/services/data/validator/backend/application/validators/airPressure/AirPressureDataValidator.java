package pt.sensae.services.data.validator.backend.application.validators.airPressure;

import pt.sharespot.iot.core.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.properties.PropertyName;
import pt.sensae.services.data.validator.backend.application.validators.DataValidator;

public class AirPressureDataValidator implements DataValidator {

    @Override
    public DataLegitimacyOptions validate(ProcessedSensorDataDTO data) {
        if (data.hasProperty(PropertyName.AIR_PRESSURE)) {
            if (data.getSensorData().airPressure.hectoPascal < 950 || data.getSensorData().airPressure.hectoPascal > 1050) {
                return DataLegitimacyOptions.INCORRECT;
            }
        }

        return DataLegitimacyOptions.CORRECT;
    }
}
