package pt.sensae.services.device.management.slave.backend.infrastructure.boot.config;

import org.springframework.context.annotation.Configuration;
import pt.sensae.services.device.management.slave.backend.application.RoutingKeysProvider;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeysFactory;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeysFactory;

@Configuration
public class RoutingKeysConfiguration implements RoutingKeysProvider {

    @Override
    public SensorRoutingKeys.SensorRoutingKeysBuilder getSensorTopicBuilder(RoutingKeysBuilderOptions options) {
        return new SensorRoutingKeysFactory().getBuilder(ContainerTypeOptions.DEVICE_MANAGEMENT, options);
    }

    @Override
    public InternalRoutingKeys.InternalRoutingKeysBuilder getInternalTopicBuilder(RoutingKeysBuilderOptions options) {
        return new InternalRoutingKeysFactory().getBuilder(ContainerTypeOptions.DEVICE_MANAGEMENT, options);
    }
}
