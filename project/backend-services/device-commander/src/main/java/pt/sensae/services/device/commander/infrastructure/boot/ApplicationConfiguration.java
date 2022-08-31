package pt.sensae.services.device.commander.infrastructure.boot;

import io.quarkus.runtime.StartupEvent;
import pt.sensae.services.device.commander.application.RoutingKeysProvider;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class ApplicationConfiguration {

    @Inject
    RoutingKeysProvider provider;

    void onStart(@Observes StartupEvent ev) {
        var keys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withContextType(ContextTypeOptions.DEVICE_INFORMATION)
                .withContainerType(ContainerTypeOptions.DEVICE_MANAGEMENT)
                .withOperationType(OperationTypeOptions.INFO)
                .missingAsAny();
        if (keys.isEmpty()) {
            throw new RuntimeException("Error creating Routing Keys");
        }

        System.setProperty("mp.messaging.incoming.ingress-commands.routing-keys", "#");
        System.setProperty("mp.messaging.incoming.ingress-device-informations.routing-keys", keys.get()
                .toString());
    }
}
