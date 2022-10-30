package pt.sensae.services.data.validator.application.validators.temperature;

import pt.sensae.services.data.validator.application.validators.DataValidator;
import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sharespot.iot.core.data.model.properties.PropertyName;
import pt.sharespot.iot.core.data.routing.keys.DataLegitimacyOptions;

public class TemperatureDataValidator implements DataValidator {

    @Override
    public DataLegitimacyOptions validate(DataUnitDTO data) {
        if (data.hasProperty(PropertyName.TEMPERATURE)) {
            if (data.getSensorData().temperature.celsius < -10 || data.getSensorData().temperature.celsius > 60) {
                return DataLegitimacyOptions.INCORRECT;
            }
        }

        return DataLegitimacyOptions.CORRECT;
    }
}
