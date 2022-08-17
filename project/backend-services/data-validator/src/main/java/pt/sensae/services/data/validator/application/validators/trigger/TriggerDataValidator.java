package pt.sensae.services.data.validator.application.validators.trigger;

import pt.sensae.services.data.validator.application.validators.DataValidator;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.routing.keys.DataLegitimacyOptions;

public class TriggerDataValidator implements DataValidator {

    @Override
    public DataLegitimacyOptions validate(SensorDataDTO data) {
        return DataLegitimacyOptions.CORRECT;
    }
}
