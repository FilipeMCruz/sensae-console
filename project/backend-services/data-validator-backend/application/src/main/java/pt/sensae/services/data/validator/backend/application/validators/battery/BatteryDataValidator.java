package pt.sensae.services.data.validator.backend.application.validators.battery;

import pt.sensae.services.data.validator.backend.application.validators.DataValidator;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.model.properties.PropertyName;
import pt.sharespot.iot.core.sensor.routing.keys.DataLegitimacyOptions;

public class BatteryDataValidator implements DataValidator {

    @Override
    public DataLegitimacyOptions validate(SensorDataDTO data) {
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
