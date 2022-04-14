package pt.sensae.services.smart.irrigation.backend.application;

import pt.sharespot.iot.core.routing.keys.RoutingKeys;
import pt.sharespot.iot.core.routing.keys.RoutingKeysBuilderOptions;

public interface RoutingKeysProvider {
    RoutingKeys.RoutingKeysBuilder getBuilder(RoutingKeysBuilderOptions options);
}
