package pt.sensae.services.rule.management.application;

import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

public interface RoutingKeysProvider {
    InternalRoutingKeys.Builder getInternalTopicBuilder(RoutingKeysBuilderOptions options);
}
