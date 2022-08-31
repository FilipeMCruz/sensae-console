package pt.sensae.services.fleet.management.backend.application;

import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

public interface RoutingKeysProvider {
    DataRoutingKeys.Builder getBuilder(RoutingKeysBuilderOptions options);
}
