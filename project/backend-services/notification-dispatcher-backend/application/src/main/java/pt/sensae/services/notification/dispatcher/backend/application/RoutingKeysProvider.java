package pt.sensae.services.notification.dispatcher.backend.application;

import pt.sharespot.iot.core.alert.routing.keys.AlertRoutingKeys;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

public interface RoutingKeysProvider {

    InternalRoutingKeys.Builder getInternalTopicBuilder(RoutingKeysBuilderOptions options);

    AlertRoutingKeys.Builder getAlertTopicBuilder(RoutingKeysBuilderOptions options);
}
