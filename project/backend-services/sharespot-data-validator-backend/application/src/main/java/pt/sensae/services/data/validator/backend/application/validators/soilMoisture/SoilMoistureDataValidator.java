package pt.sensae.services.data.validator.backend.application.validators.soilMoisture;

import pt.sharespot.iot.core.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.properties.PropertyName;
import pt.sensae.services.data.validator.backend.application.validators.DataValidator;

public class SoilMoistureDataValidator implements DataValidator {

    @Override
    public DataLegitimacyOptions validate(ProcessedSensorDataDTO data) {
        if (data.hasProperty(PropertyName.SOIL_MOISTURE)) {
            if (data.getSensorData().soilMoisture.relativePercentage < 0 || data.getSensorData().soilMoisture.relativePercentage > 100) {
                return DataLegitimacyOptions.INCORRECT;
            }
        }

        return DataLegitimacyOptions.CORRECT;
    }
}
