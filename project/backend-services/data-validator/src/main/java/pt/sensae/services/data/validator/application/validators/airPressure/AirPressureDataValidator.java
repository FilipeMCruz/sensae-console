package pt.sensae.services.data.validator.application.validators.airPressure;

import pt.sensae.services.data.validator.application.validators.DataValidator;
import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sharespot.iot.core.data.model.properties.PropertyName;
import pt.sharespot.iot.core.data.routing.keys.DataLegitimacyOptions;

public class AirPressureDataValidator implements DataValidator {

    @Override
    public DataLegitimacyOptions validate(DataUnitDTO data) {
        if (data.hasProperty(PropertyName.AIR_PRESSURE)) {
            if (data.getSensorData().airPressure.hectoPascal < 950 || data.getSensorData().airPressure.hectoPascal > 1050) {
                return DataLegitimacyOptions.INCORRECT;
            }
        }

        return DataLegitimacyOptions.CORRECT;
    }
}
