package pt.sensae.services.device.ownership.flow.application;

import pt.sharespot.iot.core.alert.routing.keys.AlertRoutingKeys;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;

public interface RoutingKeysProvider {

    SensorRoutingKeys.Builder getDataTopicBuilder(RoutingKeysBuilderOptions options);

    InternalRoutingKeys.Builder getInternalTopicBuilder(RoutingKeysBuilderOptions options);

    AlertRoutingKeys.Builder getAlertTopicBuilder(RoutingKeysBuilderOptions options);
}
