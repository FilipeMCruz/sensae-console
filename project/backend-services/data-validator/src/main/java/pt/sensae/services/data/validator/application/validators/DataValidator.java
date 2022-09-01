package pt.sensae.services.data.validator.application.validators;

import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sharespot.iot.core.data.routing.keys.DataLegitimacyOptions;

public interface DataValidator {
    DataLegitimacyOptions validate(DataUnitDTO data);
}
