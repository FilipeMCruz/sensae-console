package sharespot.services.lgt92gpsdatagateway.infrastructure.boot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import pt.sharespot.iot.core.routing.keys.RoutingKeys;
import pt.sharespot.iot.core.routing.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.routing.keys.RoutingKeysFactory;
import sharespot.services.lgt92gpsdatagateway.application.RoutingKeysProvider;

@Configuration
public class RoutingKeysConfiguration implements RoutingKeysProvider {

    @Value("sharespot.container.name")
    public String containerName;

    @Value("sharespot.container.type")
    public String containerType;

    @Override
    public RoutingKeys.RoutingKeysBuilder getBuilder(RoutingKeysBuilderOptions options) {
        return new RoutingKeysFactory().getBuilder(containerName, containerType, options);
    }
}
