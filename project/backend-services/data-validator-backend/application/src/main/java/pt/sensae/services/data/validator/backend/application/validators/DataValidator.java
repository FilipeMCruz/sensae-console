package pt.sensae.services.data.validator.backend.application.validators;

import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.routing.keys.DataLegitimacyOptions;

public interface DataValidator {
    DataLegitimacyOptions validate(SensorDataDTO data);
}
