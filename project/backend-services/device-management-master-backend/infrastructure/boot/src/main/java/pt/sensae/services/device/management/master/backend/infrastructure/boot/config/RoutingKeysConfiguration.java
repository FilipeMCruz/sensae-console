package pt.sensae.services.device.management.master.backend.infrastructure.boot.config;

import org.springframework.context.annotation.Configuration;
import pt.sensae.services.device.management.master.backend.application.RoutingKeysProvider;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeysFactory;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

@Configuration
public class RoutingKeysConfiguration implements RoutingKeysProvider {

    @Override
    public InternalRoutingKeys.InternalRoutingKeysBuilder getInternalTopicBuilder(RoutingKeysBuilderOptions options) {
        return new InternalRoutingKeysFactory().getBuilder(ContainerTypeOptions.DEVICE_MANAGEMENT, options);
    }
}
