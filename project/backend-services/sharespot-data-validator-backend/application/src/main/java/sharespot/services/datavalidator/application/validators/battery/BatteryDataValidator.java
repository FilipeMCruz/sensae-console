package sharespot.services.datavalidator.application.validators.battery;

import pt.sharespot.iot.core.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.properties.PropertyName;
import sharespot.services.datavalidator.application.validators.DataValidator;

public class BatteryDataValidator implements DataValidator {

    @Override
    public DataLegitimacyOptions validate(ProcessedSensorDataDTO data) {
        if (data.hasProperty(PropertyName.BATTERY_PERCENTAGE)) {
            return DataLegitimacyOptions.UNDETERMINED;
        }
        if (data.hasProperty(PropertyName.BATTERY_VOLTS)) {
            return DataLegitimacyOptions.UNDETERMINED;
        }
        return DataLegitimacyOptions.CORRECT;
    }
}
