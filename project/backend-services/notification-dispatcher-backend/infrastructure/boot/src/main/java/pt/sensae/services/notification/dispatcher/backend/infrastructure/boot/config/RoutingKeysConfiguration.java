package pt.sensae.services.notification.dispatcher.backend.infrastructure.boot.config;

import org.springframework.context.annotation.Configuration;
import pt.sensae.services.notification.dispatcher.backend.application.RoutingKeysProvider;
import pt.sharespot.iot.core.alert.routing.keys.AlertRoutingKeys;
import pt.sharespot.iot.core.alert.routing.keys.AlertRoutingKeysFactory;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeysFactory;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

@Configuration
public class RoutingKeysConfiguration implements RoutingKeysProvider {

    @Override
    public InternalRoutingKeys.Builder getInternalTopicBuilder(RoutingKeysBuilderOptions options) {
        return new InternalRoutingKeysFactory().getBuilder(ContainerTypeOptions.NOTIFICATION_MANAGEMENT, options);
    }

    @Override
    public AlertRoutingKeys.Builder getAlertTopicBuilder(RoutingKeysBuilderOptions options) {
        return new AlertRoutingKeysFactory().getBuilder(ContainerTypeOptions.NOTIFICATION_MANAGEMENT, options);
    }
}
