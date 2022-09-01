package pt.sensae.services.data.validator.application.validators.battery;

import pt.sensae.services.data.validator.application.validators.DataValidator;
import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sharespot.iot.core.data.model.properties.PropertyName;
import pt.sharespot.iot.core.data.routing.keys.DataLegitimacyOptions;

public class BatteryDataValidator implements DataValidator {

    @Override
    public DataLegitimacyOptions validate(DataUnitDTO data) {
        if (data.hasProperty(PropertyName.BATTERY_MIN_VOLTS)) {
            if (data.getSensorData().battery.minVolts < 0) {
                return DataLegitimacyOptions.INCORRECT;
            }
        }
        if (data.hasAllProperties(PropertyName.BATTERY_MIN_VOLTS, PropertyName.BATTERY_MAX_VOLTS)) {
            if (data.getSensorData().battery.maxVolts < data.getSensorData().battery.minVolts) {
                return DataLegitimacyOptions.INCORRECT;
            }
        }

        if (data.hasProperty(PropertyName.BATTERY_PERCENTAGE)) {
            if (data.getSensorData().battery.percentage < 0 || data.getSensorData().battery.percentage > 100) {
                return DataLegitimacyOptions.INCORRECT;
            }
        }
        if (data.hasProperty(PropertyName.BATTERY_VOLTS)) {
            if (data.getSensorData().battery.volts < 0) {
                return DataLegitimacyOptions.INCORRECT;
            }
            if (data.hasProperty(PropertyName.BATTERY_MAX_VOLTS)) {
                if (data.getSensorData().battery.maxVolts < data.getSensorData().battery.volts) {
                    return DataLegitimacyOptions.INCORRECT;
                }
            }
            if (data.hasProperty(PropertyName.BATTERY_MIN_VOLTS)) {
                if (data.getSensorData().battery.minVolts > data.getSensorData().battery.volts) {
                    return DataLegitimacyOptions.INCORRECT;
                }
            }
        }
        return DataLegitimacyOptions.CORRECT;
    }
}
