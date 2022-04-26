package pt.sensae.services.data.validator.backend.application.validators.soilConductivity;

import pt.sensae.services.data.validator.backend.application.validators.DataValidator;
import pt.sharespot.iot.core.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.properties.PropertyName;

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
