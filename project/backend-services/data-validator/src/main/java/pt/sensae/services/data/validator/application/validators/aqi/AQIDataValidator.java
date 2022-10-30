package pt.sensae.services.data.validator.application.validators.aqi;

import pt.sensae.services.data.validator.application.validators.DataValidator;
import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sharespot.iot.core.data.routing.keys.DataLegitimacyOptions;

public class AQIDataValidator implements DataValidator {

    @Override
    public DataLegitimacyOptions validate(DataUnitDTO data) {
        return DataLegitimacyOptions.CORRECT;
    }
}
