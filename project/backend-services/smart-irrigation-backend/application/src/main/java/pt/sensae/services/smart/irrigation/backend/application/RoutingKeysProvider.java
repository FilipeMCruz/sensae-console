package pt.sensae.services.smart.irrigation.backend.application;

import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;

public interface RoutingKeysProvider {
    SensorRoutingKeys.Builder getBuilder(RoutingKeysBuilderOptions options);
}
