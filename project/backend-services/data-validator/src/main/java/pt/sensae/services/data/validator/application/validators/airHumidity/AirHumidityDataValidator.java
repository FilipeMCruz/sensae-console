package pt.sensae.services.data.validator.application.validators.airHumidity;

import pt.sensae.services.data.validator.application.validators.DataValidator;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.model.properties.PropertyName;
import pt.sharespot.iot.core.sensor.routing.keys.DataLegitimacyOptions;

public class AirHumidityDataValidator implements DataValidator {

    /**
     * This assumes that the temperature varies between -15 and 30 C.
     * Taken from @see <a href="https://www.engineeringtoolbox.com/maximum-moisture-content-air-d_1403.html">maximum moisture in air</a>
     */
    @Override
    public DataLegitimacyOptions validate(SensorDataDTO data) {
        if (data.hasProperty(PropertyName.AIR_HUMIDITY_GRAMS_PER_CUBIC_METER)) {
            if (data.getSensorData().airHumidity.gramsPerCubicMeter < 0 || data.getSensorData().airHumidity.gramsPerCubicMeter > 30)
                return DataLegitimacyOptions.INCORRECT;
        }
        if (data.hasProperty(PropertyName.AIR_HUMIDITY_RELATIVE_PERCENTAGE)) {
            if (data.getSensorData().airHumidity.relativePercentage < 0 || data.getSensorData().airHumidity.relativePercentage > 100) {
                return DataLegitimacyOptions.INCORRECT;
            }
        }
        return DataLegitimacyOptions.CORRECT;
    }
}
