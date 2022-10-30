package pt.sensae.services.device.ownership.flow.application;

import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sharespot.iot.core.keys.OwnershipOptions;
import pt.sharespot.iot.core.keys.RoutingKeys;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class DataUnitProcessor {

    @Inject
    DataEnricher enricher;

    @Inject
    RoutingKeysProvider provider;

    @Inject
    DataUnitPublisher publisher;

    public void publish(MessageConsumed<DataUnitDTO, DataRoutingKeys> message) {
        message.toSupplied(this::inToOutData, this::inToOutKeys).ifPresent(publisher::next);
    }

    private Optional<DataUnitDTO> inToOutData(DataUnitDTO node, DataRoutingKeys keys) {
        return enricher.tryToAppend(node);
    }

    private Optional<DataRoutingKeys> inToOutKeys(DataUnitDTO data, DataRoutingKeys keys) {
        return provider.getDataTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withUpdated(data)
                .withOwnership(OwnershipOptions.WITH_DOMAIN_OWNERSHIP)
                .from(keys);
    }
}
