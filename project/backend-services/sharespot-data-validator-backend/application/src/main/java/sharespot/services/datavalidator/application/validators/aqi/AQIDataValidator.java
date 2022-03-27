package sharespot.services.datavalidator.application.validators.aqi;

import pt.sharespot.iot.core.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.properties.PropertyName;
import sharespot.services.datavalidator.application.validators.DataValidator;

public class AQIDataValidator implements DataValidator {

    @Override
    public DataLegitimacyOptions validate(ProcessedSensorDataDTO data) {
        if (!data.hasProperty(PropertyName.AQI)) {
            return DataLegitimacyOptions.CORRECT;
        }
        
        return DataLegitimacyOptions.UNDETERMINED;
    }
}
