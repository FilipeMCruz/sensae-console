package sharespot.services.datavalidator.application.validators.illuminance;

import pt.sharespot.iot.core.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.properties.PropertyName;
import sharespot.services.datavalidator.application.validators.DataValidator;

public class IlluminanceDataValidator implements DataValidator {

    @Override
    public DataLegitimacyOptions validate(ProcessedSensorDataDTO data) {
        if (data.hasProperty(PropertyName.ILLUMINANCE)) {
            return DataLegitimacyOptions.UNDETERMINED;
        }

        return DataLegitimacyOptions.CORRECT;
    }
}
