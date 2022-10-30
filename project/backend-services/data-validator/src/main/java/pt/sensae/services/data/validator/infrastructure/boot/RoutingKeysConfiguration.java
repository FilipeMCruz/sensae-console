package pt.sensae.services.data.validator.infrastructure.boot;

import pt.sensae.services.data.validator.application.RoutingKeysProvider;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeysFactory;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RoutingKeysConfiguration implements RoutingKeysProvider {

    @Override
    public DataRoutingKeys.Builder getBuilder(RoutingKeysBuilderOptions options) {
        return new DataRoutingKeysFactory().getBuilder(ContainerTypeOptions.DATA_VALIDATOR, options);
    }
}
