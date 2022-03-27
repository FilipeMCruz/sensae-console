package sharespot.services.datavalidator.application.validators.pressure;

import pt.sharespot.iot.core.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.properties.PropertyName;
import sharespot.services.datavalidator.application.validators.DataValidator;

public class PressureDataValidator implements DataValidator {

    @Override
    public DataLegitimacyOptions validate(ProcessedSensorDataDTO data) {
        if (data.hasProperty(PropertyName.PRESSURE)) {
            return DataLegitimacyOptions.UNDETERMINED;
        }

        return DataLegitimacyOptions.CORRECT;
    }
}
