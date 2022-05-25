package pt.sensae.services.rule.management.backend.infrastructure.boot.config;

import org.springframework.context.annotation.Configuration;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeysFactory;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sensae.services.rule.management.backend.application.RoutingKeysProvider;

@Configuration
public class RoutingKeysConfiguration implements RoutingKeysProvider {

    @Override
    public InternalRoutingKeys.Builder getInternalBuilder(RoutingKeysBuilderOptions options) {
        return new InternalRoutingKeysFactory().getBuilder(ContainerTypeOptions.RULE_MANAGEMENT, options);
    }
}
