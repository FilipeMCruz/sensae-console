package pt.sensae.services.data.validator.application.validators.o3;

import pt.sensae.services.data.validator.application.validators.DataValidator;
import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sharespot.iot.core.data.model.properties.PropertyName;
import pt.sharespot.iot.core.data.routing.keys.DataLegitimacyOptions;

public class O3DataValidator implements DataValidator {
    @Override
    public DataLegitimacyOptions validate(DataUnitDTO data) {
        if (data.hasProperty(PropertyName.O3)) {
            if (data.getSensorData().o3.ppm < 0) {
                return DataLegitimacyOptions.INCORRECT;
            }
        }
        return DataLegitimacyOptions.CORRECT;
    }
}
