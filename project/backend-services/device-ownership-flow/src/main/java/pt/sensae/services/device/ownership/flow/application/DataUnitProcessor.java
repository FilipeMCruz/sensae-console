package pt.sensae.services.device.ownership.flow.application;

import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sharespot.iot.core.keys.OwnershipOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;

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

    public void publish(MessageConsumed<SensorDataDTO, SensorRoutingKeys> message) {
        message.toSupplied(this::inToOutData, this::inToOutKeys).ifPresent(publisher::next);
    }

    private Optional<SensorDataDTO> inToOutData(SensorDataDTO node, SensorRoutingKeys keys) {
        return enricher.tryToAppend(node);
    }

    private Optional<SensorRoutingKeys> inToOutKeys(SensorDataDTO data, SensorRoutingKeys keys) {
        return provider.getDataTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withUpdated(data)
                .withOwnership(OwnershipOptions.WITH_DOMAIN_OWNERSHIP)
                .from(keys);
    }
}
