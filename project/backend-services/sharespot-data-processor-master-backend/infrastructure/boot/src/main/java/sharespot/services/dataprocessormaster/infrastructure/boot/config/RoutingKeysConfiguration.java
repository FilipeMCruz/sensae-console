package sharespot.services.dataprocessormaster.infrastructure.boot.config;

import org.springframework.context.annotation.Configuration;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeysFactory;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import sharespot.services.dataprocessormaster.application.RoutingKeysProvider;

@Configuration
public class RoutingKeysConfiguration implements RoutingKeysProvider {

    @Override
    public InternalRoutingKeys.Builder getInternalTopicBuilder(RoutingKeysBuilderOptions options) {
        return new InternalRoutingKeysFactory().getBuilder(ContainerTypeOptions.DEVICE_MANAGEMENT, options);
    }
}
