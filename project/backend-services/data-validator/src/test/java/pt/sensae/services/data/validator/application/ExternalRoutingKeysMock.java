package pt.sensae.services.data.validator.application;

import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeysFactory;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

public class ExternalRoutingKeysMock implements RoutingKeysProvider {

    @Override
    public DataRoutingKeys.Builder getBuilder(RoutingKeysBuilderOptions options) {
        return new DataRoutingKeysFactory().getBuilder(ContainerTypeOptions.DATA_PROCESSOR, options);
    }
}
