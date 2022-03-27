package sharespot.services.datavalidator.application.validators.moisture;

import pt.sharespot.iot.core.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.properties.PropertyName;
import sharespot.services.datavalidator.application.validators.DataValidator;

public class MoistureDataValidator implements DataValidator {

    @Override
    public DataLegitimacyOptions validate(ProcessedSensorDataDTO data) {
        if (data.hasProperty(PropertyName.SOIL_MOISTURE)) {
            if (data.data.moisture.percentage < 0 || data.data.moisture.percentage > 100) {
                return DataLegitimacyOptions.INCORRECT;
            }
        }

        return DataLegitimacyOptions.CORRECT;
    }
}
