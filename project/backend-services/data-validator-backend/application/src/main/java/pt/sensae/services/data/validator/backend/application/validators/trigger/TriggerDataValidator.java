package pt.sensae.services.data.validator.backend.application.validators.trigger;

import pt.sharespot.iot.core.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sensae.services.data.validator.backend.application.validators.DataValidator;

public class TriggerDataValidator implements DataValidator {

    @Override
    public DataLegitimacyOptions validate(ProcessedSensorDataDTO data) {
        return DataLegitimacyOptions.CORRECT;
    }
}
