package pt.sensae.services.data.validator.infrastructure.boot;

import io.quarkus.runtime.StartupEvent;
import pt.sensae.services.data.validator.application.RoutingKeysProvider;
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
        var keys = provider.getBuilder(RoutingKeysBuilderOptions.CONSUMER)
                .withInfoType(InfoTypeOptions.PROCESSED)
                .withLegitimacyType(DataLegitimacyOptions.UNKNOWN)
                .withOwnership(OwnershipOptions.WITH_DOMAIN_OWNERSHIP)
                .withRecords(RecordsOptions.WITH_RECORDS)
                .missingAsAny();
        if (keys.isEmpty()) {
            throw new RuntimeException("Error creating Routing Keys");
        }
        System.setProperty("mp.messaging.incoming.ingress-data-units.routing-keys", keys.get().toString());
    }
}
