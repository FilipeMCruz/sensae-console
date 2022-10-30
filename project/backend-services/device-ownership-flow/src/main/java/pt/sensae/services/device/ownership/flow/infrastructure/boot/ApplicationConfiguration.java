package pt.sensae.services.device.ownership.flow.infrastructure.boot;

import io.quarkus.runtime.StartupEvent;
import pt.sensae.services.device.ownership.flow.application.RoutingKeysProvider;
import pt.sharespot.iot.core.data.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.data.routing.keys.InfoTypeOptions;
import pt.sharespot.iot.core.data.routing.keys.RecordsOptions;
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
        var dataKeys = provider.getDataTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withInfoType(InfoTypeOptions.PROCESSED)
                .withOwnership(OwnershipOptions.UNIDENTIFIED_DOMAIN_OWNERSHIP)
                .withRecords(RecordsOptions.UNIDENTIFIED_RECORDS)
                .withLegitimacyType(DataLegitimacyOptions.UNKNOWN)
                .missingAsAny()
                .orElseThrow(() -> new RuntimeException("Error creating Routing Keys"));

        var alertKeys = provider.getAlertTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withOwnershipType(OwnershipOptions.UNIDENTIFIED_DOMAIN_OWNERSHIP)
                .withContainerType(ContainerTypeOptions.ALERT_DISPATCHER)
                .missingAsAny()
                .orElseThrow(() -> new RuntimeException("Error creating Routing Keys"));

        var processorsKeys = provider.getInternalTopicBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withContextType(ContextTypeOptions.DEVICE_IDENTITY)
                .withContainerType(ContainerTypeOptions.IDENTITY_MANAGEMENT)
                .withOperationType(OperationTypeOptions.INFO)
                .missingAsAny()
                .orElseThrow(() -> new RuntimeException("Error creating Routing Keys"));

        System.setProperty("mp.messaging.incoming.ingress-data-units.routing-keys", dataKeys.toString());
        System.setProperty("mp.messaging.incoming.ingress-alerts.routing-keys", alertKeys.toString());
        System.setProperty("mp.messaging.incoming.ingress-device-ownership.routing-keys", processorsKeys.toString());
    }
}
