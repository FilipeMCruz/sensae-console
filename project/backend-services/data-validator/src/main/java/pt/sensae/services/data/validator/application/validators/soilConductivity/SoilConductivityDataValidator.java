package pt.sensae.services.data.validator.application.validators.soilConductivity;

import pt.sensae.services.data.validator.application.validators.DataValidator;
import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sharespot.iot.core.data.model.properties.PropertyName;
import pt.sharespot.iot.core.data.routing.keys.DataLegitimacyOptions;

public class SoilConductivityDataValidator implements DataValidator {

    @Override
    public DataLegitimacyOptions validate(DataUnitDTO data) {
        if (data.hasProperty(PropertyName.SOIL_CONDUCTIVITY)) {
            if (data.getSensorData().soilConductivity.microSiemensPerCentimeter < 0) {
                return DataLegitimacyOptions.INCORRECT;
            }
            if (data.getSensorData().soilConductivity.microSiemensPerCentimeter > 20000) {
                return DataLegitimacyOptions.INCORRECT;
            }
        }

        return DataLegitimacyOptions.CORRECT;
    }
}
