package pt.sensae.services.data.validator.application.validators.waterPressure;

import pt.sensae.services.data.validator.application.validators.DataValidator;
import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sharespot.iot.core.data.model.properties.PropertyName;
import pt.sharespot.iot.core.data.routing.keys.DataLegitimacyOptions;

public class WaterPressureDataValidator implements DataValidator {

    @Override
    public DataLegitimacyOptions validate(DataUnitDTO data) {
        if (data.hasProperty(PropertyName.VOC)) {
            if (data.getSensorData().waterPressure.bar < 0.6) {
                return DataLegitimacyOptions.INCORRECT;
            }
            if (data.getSensorData().waterPressure.bar > 10) {
                return DataLegitimacyOptions.INCORRECT;
            }
        }
        return DataLegitimacyOptions.CORRECT;
    }
}
