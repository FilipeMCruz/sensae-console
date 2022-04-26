package pt.sensae.services.data.validator.backend.application.validators.waterPressure;

import pt.sensae.services.data.validator.backend.application.validators.DataValidator;
import pt.sharespot.iot.core.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.properties.PropertyName;

public class WaterPressureDataValidator implements DataValidator {
    @Override
    public DataLegitimacyOptions validate(ProcessedSensorDataDTO data) {
        if (data.hasProperty(PropertyName.VOC)) {
            if (data.getSensorData().waterPressure.bar < 0.6) {
                return DataLegitimacyOptions.INCORRECT;
            }
            if (data.getSensorData().waterPressure.bar > 10) {
                return DataLegitimacyOptions.INCORRECT;
            }
        }
        return DataLegitimacyOptions.CORRECT;
    }
}
