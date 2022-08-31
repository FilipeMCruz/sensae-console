package pt.sensae.services.data.validator.application;

import pt.sensae.services.data.validator.application.validators.DataValidator;
import pt.sensae.services.data.validator.application.validators.ValidatorsFactory;
import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sharespot.iot.core.data.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class DataValidatorService {

    private final RoutingKeysProvider provider;

    private final List<DataValidator> validators;

    public DataValidatorService(RoutingKeysProvider provider) {
        this.provider = provider;
        this.validators = ValidatorsFactory.buildValidators();
    }

    public Optional<DataRoutingKeys> decide(DataUnitDTO data, DataRoutingKeys keys) {
        var outcomes = validators.stream().map(v -> v.validate(data)).toList();
        if (outcomes.contains(DataLegitimacyOptions.UNDETERMINED)) {
            return keysWith(data, keys, DataLegitimacyOptions.UNDETERMINED);
        } else if (outcomes.contains(DataLegitimacyOptions.INCORRECT)) {
            return keysWith(data, keys, DataLegitimacyOptions.INCORRECT);
        } else {
            return keysWith(data, keys, DataLegitimacyOptions.CORRECT);
        }
    }

    private Optional<DataRoutingKeys> keysWith(DataUnitDTO data, DataRoutingKeys keys, DataLegitimacyOptions legitimacyOptions) {
        return provider.getBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withUpdated(data)
                .withLegitimacyType(legitimacyOptions)
                .from(keys);
    }
}
