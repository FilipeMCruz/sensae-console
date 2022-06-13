package pt.sensae.services.fleet.management.backend.infrastructure.boot.config;

import org.springframework.context.annotation.Configuration;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeysFactory;
import pt.sensae.services.fleet.management.backend.application.RoutingKeysProvider;

@Configuration
public class RoutingKeysConfiguration implements RoutingKeysProvider {

    @Override
    public SensorRoutingKeys.Builder getBuilder(RoutingKeysBuilderOptions options) {
        return new SensorRoutingKeysFactory().getBuilder(ContainerTypeOptions.FLEET_MANAGEMENT, options);
    }
}
