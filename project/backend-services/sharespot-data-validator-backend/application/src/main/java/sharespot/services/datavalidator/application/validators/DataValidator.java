package sharespot.services.datavalidator.application.validators;

import pt.sharespot.iot.core.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;

public interface DataValidator {
    DataLegitimacyOptions validate(ProcessedSensorDataDTO data);
}
