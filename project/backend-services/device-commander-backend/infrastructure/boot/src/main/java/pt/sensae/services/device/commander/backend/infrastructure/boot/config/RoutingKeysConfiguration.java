package pt.sensae.services.device.commander.backend.infrastructure.boot.config;

import org.springframework.context.annotation.Configuration;
import pt.sensae.services.device.commander.backend.application.RoutingKeysProvider;
import pt.sharespot.iot.core.routing.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.routing.keys.RoutingKeys;
import pt.sharespot.iot.core.routing.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.routing.keys.RoutingKeysFactory;

@Configuration
public class RoutingKeysConfiguration implements RoutingKeysProvider {

    @Override
    //TODO: add new Container type
    public RoutingKeys.RoutingKeysBuilder getBuilder(RoutingKeysBuilderOptions options) {
        return new RoutingKeysFactory().getBuilder(ContainerTypeOptions.OTHER, options);
    }
}
