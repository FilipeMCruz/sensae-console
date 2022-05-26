package pt.sensae.services.alert.dispatcher.backend.application;

import pt.sharespot.iot.core.alert.routing.keys.AlertRoutingKeys;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;

public interface RoutingKeysProvider {
    SensorRoutingKeys.Builder getSensorBuilder(RoutingKeysBuilderOptions options);
    
    AlertRoutingKeys.Builder getAlertBuilder(RoutingKeysBuilderOptions options);
    
    InternalRoutingKeys.Builder getInternalBuilder(RoutingKeysBuilderOptions options);
}
