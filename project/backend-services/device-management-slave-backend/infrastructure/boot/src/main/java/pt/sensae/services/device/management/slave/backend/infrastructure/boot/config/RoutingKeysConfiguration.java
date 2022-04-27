package pt.sensae.services.device.management.slave.backend.infrastructure.boot.config;

import org.springframework.context.annotation.Configuration;
import pt.sensae.services.device.management.slave.backend.application.RoutingKeysProvider;
import pt.sharespot.iot.core.routing.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.routing.keys.RoutingKeys;
import pt.sharespot.iot.core.routing.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.routing.keys.RoutingKeysFactory;

@Configuration
public class RoutingKeysConfiguration implements RoutingKeysProvider {

    @Override
    public RoutingKeys.RoutingKeysBuilder getBuilder(RoutingKeysBuilderOptions options) {
        return new RoutingKeysFactory().getBuilder(ContainerTypeOptions.DEVICE_MANAGEMENT, options);
    }
}
