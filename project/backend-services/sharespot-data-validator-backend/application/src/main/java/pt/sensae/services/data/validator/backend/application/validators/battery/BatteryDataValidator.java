package pt.sensae.services.data.validator.backend.application.validators.battery;

import pt.sharespot.iot.core.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.properties.PropertyName;
import pt.sensae.services.data.validator.backend.application.validators.DataValidator;

public class BatteryDataValidator implements DataValidator {

    @Override
    public DataLegitimacyOptions validate(ProcessedSensorDataDTO data) {
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
