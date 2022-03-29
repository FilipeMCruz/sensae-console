package sharespot.services.datavalidator.application.validators.motion;

import pt.sharespot.iot.core.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import sharespot.services.datavalidator.application.validators.DataValidator;

public class MotionDataValidator implements DataValidator {

    @Override
    public DataLegitimacyOptions validate(ProcessedSensorDataDTO data) {
        return DataLegitimacyOptions.CORRECT;
    }
}
