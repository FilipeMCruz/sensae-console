package pt.sensae.services.device.commander.backend.infrastructure.boot.config;

import org.springframework.context.annotation.Configuration;
import pt.sensae.services.device.commander.backend.application.RoutingKeysProvider;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeysFactory;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

@Configuration
public class RoutingKeysConfiguration implements RoutingKeysProvider {

    @Override
    public InternalRoutingKeys.Builder getBuilder(RoutingKeysBuilderOptions options) {
        return new InternalRoutingKeysFactory().getBuilder(ContainerTypeOptions.DEVICE_COMMANDER, options);
    }
}
