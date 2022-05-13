package pt.sensae.services.device.management.slave.backend.application;

import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;

public interface RoutingKeysProvider {
    SensorRoutingKeys.SensorRoutingKeysBuilder getSensorTopicBuilder(RoutingKeysBuilderOptions options);

    InternalRoutingKeys.InternalRoutingKeysBuilder getInternalTopicBuilder(RoutingKeysBuilderOptions options);
}
