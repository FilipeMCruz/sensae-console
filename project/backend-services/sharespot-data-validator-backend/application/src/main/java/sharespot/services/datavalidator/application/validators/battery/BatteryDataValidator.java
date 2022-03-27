package sharespot.services.datavalidator.application.validators.battery;

import pt.sharespot.iot.core.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.properties.PropertyName;
import sharespot.services.datavalidator.application.validators.DataValidator;

public class BatteryDataValidator implements DataValidator {

    @Override
    public DataLegitimacyOptions validate(ProcessedSensorDataDTO data) {
        if (data.hasProperty(PropertyName.BATTERY_PERCENTAGE)) {
            if (data.data.battery.percentage < 5 || data.data.battery.percentage > 100) {
                return DataLegitimacyOptions.INCORRECT;
            }
        }
        if (data.hasProperty(PropertyName.BATTERY_VOLTS)) {
            //TODO think about how to check for wrong volts values
            if (data.data.battery.volts < 0) {
                return DataLegitimacyOptions.INCORRECT;
            }
            return DataLegitimacyOptions.UNDETERMINED;
        }
        return DataLegitimacyOptions.CORRECT;
    }
}
