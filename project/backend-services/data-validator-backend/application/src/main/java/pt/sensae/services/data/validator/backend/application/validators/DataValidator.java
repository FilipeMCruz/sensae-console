package pt.sensae.services.data.validator.backend.application.validators;

import pt.sharespot.iot.core.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;

public interface DataValidator {
    DataLegitimacyOptions validate(ProcessedSensorDataDTO data);
}
