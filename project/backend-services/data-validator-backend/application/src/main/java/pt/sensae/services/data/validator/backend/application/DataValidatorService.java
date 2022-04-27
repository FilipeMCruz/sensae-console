package pt.sensae.services.data.validator.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.data.validator.backend.application.validators.DataValidator;
import pt.sensae.services.data.validator.backend.application.validators.ValidatorsFactory;
import pt.sharespot.iot.core.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.routing.keys.RoutingKeys;
import pt.sharespot.iot.core.routing.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;

import java.util.List;
import java.util.Optional;

@Service
public class DataValidatorService {

    private final RoutingKeysProvider provider;

    private final List<DataValidator> validators;

    public DataValidatorService(RoutingKeysProvider provider) {
        this.provider = provider;
        this.validators = ValidatorsFactory.buildValidators();
    }

    public Optional<RoutingKeys> decide(ProcessedSensorDataDTO data, RoutingKeys keys) {
        var outcomes = validators.stream().map(v -> v.validate(data)).toList();
        if (outcomes.contains(DataLegitimacyOptions.UNDETERMINED)) {
            return keysWith(data, keys, DataLegitimacyOptions.UNDETERMINED);
        } else if (outcomes.contains(DataLegitimacyOptions.INCORRECT)) {
            return keysWith(data, keys, DataLegitimacyOptions.INCORRECT);
        } else {
            return keysWith(data, keys, DataLegitimacyOptions.CORRECT);
        }
    }

    private Optional<RoutingKeys> keysWith(ProcessedSensorDataDTO data, RoutingKeys keys, DataLegitimacyOptions legitimacyOptions) {
        return provider.getBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withUpdated(data)
                .withLegitimacyType(legitimacyOptions)
                .from(keys);
    }
}
