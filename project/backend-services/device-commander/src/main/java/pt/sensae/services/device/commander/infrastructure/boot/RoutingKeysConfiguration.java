package pt.sensae.services.device.commander.infrastructure.boot;

import pt.sensae.services.device.commander.application.RoutingKeysProvider;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeysFactory;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeys;
import pt.sharespot.iot.core.internal.routing.keys.InternalRoutingKeysFactory;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RoutingKeysConfiguration implements RoutingKeysProvider {

    @Override
    public DataRoutingKeys.Builder getDataTopicBuilder(RoutingKeysBuilderOptions options) {
        return new DataRoutingKeysFactory().getBuilder(ContainerTypeOptions.DEVICE_MANAGEMENT, options);
    }

    @Override
    public InternalRoutingKeys.Builder getInternalTopicBuilder(RoutingKeysBuilderOptions options) {
        return new InternalRoutingKeysFactory().getBuilder(ContainerTypeOptions.DEVICE_MANAGEMENT, options);
    }
}
