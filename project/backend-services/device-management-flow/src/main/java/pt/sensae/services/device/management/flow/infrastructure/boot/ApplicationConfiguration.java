package pt.sensae.services.device.management.flow.infrastructure.boot;

import io.quarkus.runtime.StartupEvent;
import pt.sensae.services.device.management.flow.application.RoutingKeysProvider;
import pt.sharespot.iot.core.internal.routing.keys.ContextTypeOptions;
import pt.sharespot.iot.core.internal.routing.keys.OperationTypeOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.OwnershipOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.sensor.routing.keys.InfoTypeOptions;
import pt.sharespot.iot.core.sensor.routing.keys.RecordsOptions;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class ApplicationConfiguration {

    @Inject
    RoutingKeysProvider provider;

    void onStart(@Observes StartupEvent ev) {
        var dataKeys = provider.getDataTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withInfoType(InfoTypeOptions.PROCESSED)
                .withRecords(RecordsOptions.UNIDENTIFIED_RECORDS)
                .withOwnership(OwnershipOptions.WITH_DOMAIN_OWNERSHIP)
                .withLegitimacyType(DataLegitimacyOptions.UNKNOWN)
                .missingAsAny();
        if (dataKeys.isEmpty()) {
            throw new RuntimeException("Error creating Routing Keys");
        }

        var processorsKeys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withContextType(ContextTypeOptions.DEVICE_MANAGEMENT)
                .withContainerType(ContainerTypeOptions.DEVICE_MANAGEMENT)
                .withOperationType(OperationTypeOptions.INFO)
                .missingAsAny();
        if (processorsKeys.isEmpty()) {
            throw new RuntimeException("Error creating Routing Keys");
        }
        
        System.setProperty("mp.messaging.incoming.ingress-data-units.routing-keys", dataKeys.get().toString());
        System.setProperty("mp.messaging.incoming.ingress-device-informations.routing-keys", processorsKeys.get().toString());
    }
}
