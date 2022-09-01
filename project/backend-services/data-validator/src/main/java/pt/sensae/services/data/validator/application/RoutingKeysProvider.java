package pt.sensae.services.data.validator.application;

import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

public interface RoutingKeysProvider {
    DataRoutingKeys.Builder getBuilder(RoutingKeysBuilderOptions options);
}
