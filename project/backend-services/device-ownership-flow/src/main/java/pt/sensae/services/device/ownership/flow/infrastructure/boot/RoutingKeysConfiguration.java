package pt.sensae.services.device.ownership.flow.infrastructure.boot;

import pt.sensae.services.device.ownership.flow.application.RoutingKeysProvider;
import pt.sharespot.iot.core.alert.routing.keys.AlertRoutingKeys;
import pt.sharespot.iot.core.alert.routing.keys.AlertRoutingKeysFactory;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeysFactory;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeysFactory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RoutingKeysConfiguration implements RoutingKeysProvider {

    @Override
    public SensorRoutingKeys.Builder getDataTopicBuilder(RoutingKeysBuilderOptions options) {
        return new SensorRoutingKeysFactory().getBuilder(ContainerTypeOptions.IDENTITY_MANAGEMENT, options);
    }

    @Override
    public InternalRoutingKeys.Builder getInternalTopicBuilder(RoutingKeysBuilderOptions options) {
        return new InternalRoutingKeysFactory().getBuilder(ContainerTypeOptions.IDENTITY_MANAGEMENT, options);
    }

    @Override
    public AlertRoutingKeys.Builder getAlertTopicBuilder(RoutingKeysBuilderOptions options) {
        return new AlertRoutingKeysFactory().getBuilder(ContainerTypeOptions.IDENTITY_MANAGEMENT, options);
    }
}
