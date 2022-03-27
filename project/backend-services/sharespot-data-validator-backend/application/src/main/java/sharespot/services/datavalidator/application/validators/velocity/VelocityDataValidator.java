package sharespot.services.datavalidator.application.validators.velocity;

import pt.sharespot.iot.core.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.properties.PropertyName;
import sharespot.services.datavalidator.application.validators.DataValidator;

public class VelocityDataValidator implements DataValidator {

    @Override
    public DataLegitimacyOptions validate(ProcessedSensorDataDTO data) {
        if (data.hasProperty(PropertyName.VELOCITY)) {
            if (data.data.velocity.kmperh < 0 || data.data.velocity.kmperh > 150) {
                return DataLegitimacyOptions.INCORRECT;
            }
        }

        return DataLegitimacyOptions.CORRECT;
    }
}
