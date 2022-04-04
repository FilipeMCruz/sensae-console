package sharespot.services.datavalidator.application.validators.alarm;

import pt.sharespot.iot.core.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import sharespot.services.datavalidator.application.validators.DataValidator;

public class AlarmDataValidator implements DataValidator {

    @Override
    public DataLegitimacyOptions validate(ProcessedSensorDataDTO data) {
        return DataLegitimacyOptions.CORRECT;
    }
}
