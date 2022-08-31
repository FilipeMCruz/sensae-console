package pt.sensae.services.device.ownership.flow.application;

import pt.sharespot.iot.core.alert.routing.keys.AlertRoutingKeys;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

public interface RoutingKeysProvider {

    DataRoutingKeys.Builder getDataTopicBuilder(RoutingKeysBuilderOptions options);

    InternalRoutingKeys.Builder getInternalTopicBuilder(RoutingKeysBuilderOptions options);

    AlertRoutingKeys.Builder getAlertTopicBuilder(RoutingKeysBuilderOptions options);
}
