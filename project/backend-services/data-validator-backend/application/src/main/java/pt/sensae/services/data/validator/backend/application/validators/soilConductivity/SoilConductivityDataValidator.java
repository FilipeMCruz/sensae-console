package pt.sensae.services.data.validator.backend.application.validators.soilConductivity;

import pt.sensae.services.data.validator.backend.application.validators.DataValidator;
import pt.sharespot.iot.core.sensor.model.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.model.properties.PropertyName;
import pt.sharespot.iot.core.sensor.routing.keys.DataLegitimacyOptions;

public class SoilConductivityDataValidator implements DataValidator {

    @Override
    public DataLegitimacyOptions validate(ProcessedSensorDataDTO data) {
        if (data.hasProperty(PropertyName.SOIL_CONDUCTIVITY)) {
            if (data.getSensorData().soilConductivity.microSiemensPerCentimeter < 0) {
                return DataLegitimacyOptions.INCORRECT;
            }
            if (data.getSensorData().soilConductivity.microSiemensPerCentimeter > 20000) {
                return DataLegitimacyOptions.INCORRECT;
            }
        }

        return DataLegitimacyOptions.CORRECT;
    }
}
