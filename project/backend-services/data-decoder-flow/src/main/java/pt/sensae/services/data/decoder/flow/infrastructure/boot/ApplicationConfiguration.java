package pt.sensae.services.data.decoder.flow.infrastructure.boot;

import io.quarkus.runtime.StartupEvent;
import pt.sensae.services.data.decoder.flow.application.RoutingKeysProvider;
import pt.sharespot.iot.core.data.routing.keys.InfoTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.OwnershipOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class ApplicationConfiguration {

    @Inject
    RoutingKeysProvider provider;

    void onStart(@Observes StartupEvent ev) {
        var dataKeys = provider.getBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withInfoType(InfoTypeOptions.ENCODED)
                .missingAsAny();
        if (dataKeys.isEmpty()) {
            throw new RuntimeException("Error creating Routing Keys");
        }

        var decoderKeys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withContextType(ContextTypeOptions.DATA_DECODER)
                .withContainerType(ContainerTypeOptions.DATA_DECODER)
                .withOperationType(OperationTypeOptions.INFO)
                .missingAsAny();
        if (decoderKeys.isEmpty()) {
            throw new RuntimeException("Error creating Routing Keys");
        }
        
        System.setProperty("mp.messaging.incoming.ingress-data-units.routing-keys", dataKeys.get().toString());
        System.setProperty("mp.messaging.incoming.ingress-data-decoders.routing-keys", decoderKeys.get().toString());
    }
}
