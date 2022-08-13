package pt.sensae.services.data.processor.flow.infrastructure.boot;

import io.quarkus.runtime.StartupEvent;
import pt.sensae.services.data.processor.flow.application.RoutingKeysProvider;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.routing.keys.InfoTypeOptions;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class ApplicationConfiguration {

    @Inject
    RoutingKeysProvider provider;

    void onStart(@Observes StartupEvent ev) {
        var dataKeys = provider.getBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withInfoType(InfoTypeOptions.DECODED)
                .missingAsAny();
        if (dataKeys.isEmpty()) {
            throw new RuntimeException("Error creating Routing Keys");
        }

        var processorsKeys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withContextType(ContextTypeOptions.DATA_PROCESSOR)
                .withContainerType(ContainerTypeOptions.DATA_PROCESSOR)
                .withOperationType(OperationTypeOptions.INFO)
                .missingAsAny();
        if (processorsKeys.isEmpty()) {
            throw new RuntimeException("Error creating Routing Keys");
        }
        
        System.setProperty("mp.messaging.incoming.ingress-data-units.routing-keys", dataKeys.get().toString());
        System.setProperty("mp.messaging.incoming.ingress-data-processors.routing-keys", processorsKeys.get().toString());
    }
}
