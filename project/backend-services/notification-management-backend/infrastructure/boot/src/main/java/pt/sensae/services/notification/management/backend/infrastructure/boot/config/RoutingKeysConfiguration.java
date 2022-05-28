package pt.sensae.services.notification.management.backend.infrastructure.boot.config;

import org.springframework.context.annotation.Configuration;
import pt.sensae.services.notification.management.backend.application.RoutingKeysProvider;
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
        return new InternalRoutingKeysFactory().getBuilder(ContainerTypeOptions.ALERT_DISPATCHER, options);//TODO: change to NOTIFICATION_MANAGEMENT com nova versao do iot-core
    }

    @Override
    public AlertRoutingKeys.Builder getAlertTopicBuilder(RoutingKeysBuilderOptions options) {
        return new AlertRoutingKeysFactory().getBuilder(ContainerTypeOptions.ALERT_DISPATCHER, options);//TODO: change to NOTIFICATION_MANAGEMENT com nova versao do iot-core
    }
}
