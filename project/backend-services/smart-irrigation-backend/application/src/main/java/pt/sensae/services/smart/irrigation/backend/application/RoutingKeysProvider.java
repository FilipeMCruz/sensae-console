package pt.sensae.services.smart.irrigation.backend.application;

import pt.sharespot.iot.core.alert.routing.keys.AlertRoutingKeys;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

public interface RoutingKeysProvider {
    DataRoutingKeys.Builder getBuilder(RoutingKeysBuilderOptions options);
    
    AlertRoutingKeys.Builder getAlertBuilder(RoutingKeysBuilderOptions options);
}
