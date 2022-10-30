package pt.sensae.services.data.validator.application.validators.illuminance;

import pt.sensae.services.data.validator.application.validators.DataValidator;
import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sharespot.iot.core.data.model.properties.PropertyName;
import pt.sharespot.iot.core.data.routing.keys.DataLegitimacyOptions;

public class IlluminanceDataValidator implements DataValidator {

    @Override
    public DataLegitimacyOptions validate(DataUnitDTO data) {
        if (data.hasProperty(PropertyName.ILLUMINANCE)) {
            if (data.getSensorData().illuminance.lux < 0 || data.getSensorData().illuminance.lux > 100000) {
                return DataLegitimacyOptions.INCORRECT;
            }
        }

        return DataLegitimacyOptions.CORRECT;
    }
}
