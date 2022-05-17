package pt.sensae.services.data.validator.backend.application.validators.soilMoisture;

import pt.sensae.services.data.validator.backend.application.validators.DataValidator;
import pt.sharespot.iot.core.sensor.model.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.model.properties.PropertyName;
import pt.sharespot.iot.core.sensor.routing.keys.DataLegitimacyOptions;

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
