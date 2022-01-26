package sharespot.services.datavalidator.application;

import pt.sharespot.iot.core.routing.keys.RoutingKeys;
import pt.sharespot.iot.core.routing.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.routing.keys.RoutingKeysFactory;

public class InternalRoutingKeysMock implements RoutingKeysProvider {

    public String containerName = "datavalidator";

    public String containerType = "datavalidator";

    @Override
    public RoutingKeys.RoutingKeysBuilder getBuilder(RoutingKeysBuilderOptions options) {
        return new RoutingKeysFactory().getBuilder(containerName, containerType, options);
    }
}
