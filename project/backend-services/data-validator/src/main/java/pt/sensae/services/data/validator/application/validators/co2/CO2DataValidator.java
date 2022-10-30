package pt.sensae.services.data.validator.application.validators.co2;

import pt.sensae.services.data.validator.application.validators.DataValidator;
import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sharespot.iot.core.data.model.properties.PropertyName;
import pt.sharespot.iot.core.data.routing.keys.DataLegitimacyOptions;

public class CO2DataValidator implements DataValidator {
    @Override
    public DataLegitimacyOptions validate(DataUnitDTO data) {
        if (data.hasProperty(PropertyName.CO2)) {
            if (data.getSensorData().co2.ppm < 0) {
                return DataLegitimacyOptions.INCORRECT;
            }
        }
        return DataLegitimacyOptions.CORRECT;
    }
}
