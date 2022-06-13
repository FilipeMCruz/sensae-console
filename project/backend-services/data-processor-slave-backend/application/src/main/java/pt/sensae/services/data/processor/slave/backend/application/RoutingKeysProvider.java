package pt.sensae.services.data.processor.slave.backend.application;

import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;

public interface RoutingKeysProvider {
    SensorRoutingKeys.Builder getSensorTopicBuilder(RoutingKeysBuilderOptions options);

    InternalRoutingKeys.Builder getInternalTopicBuilder(RoutingKeysBuilderOptions options);

}
