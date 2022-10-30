package pt.sensae.services.device.ownership.flow.application;

import pt.sharespot.iot.core.alert.model.AlertDTO;
import pt.sharespot.iot.core.alert.routing.keys.AlertRoutingKeys;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sharespot.iot.core.keys.OwnershipOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class AlertProcessor {

    @Inject
    DataEnricher enricher;

    @Inject
    RoutingKeysProvider provider;

    @Inject
    AlertPublisher publisher;

    public void publish(MessageConsumed<AlertDTO, AlertRoutingKeys> message) {
        message.toSupplied(this::inToOutData, this::inToOutKeys).ifPresent(publisher::next);
    }

    private Optional<AlertDTO> inToOutData(AlertDTO node, AlertRoutingKeys keys) {
        return enricher.tryToAppend(node);
    }

    private Optional<AlertRoutingKeys> inToOutKeys(AlertDTO data, AlertRoutingKeys keys) {
        return provider.getAlertTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withOwnershipType(OwnershipOptions.WITH_DOMAIN_OWNERSHIP)
                .withContainerType(ContainerTypeOptions.IDENTITY_MANAGEMENT)
                .from(keys);
    }
}
