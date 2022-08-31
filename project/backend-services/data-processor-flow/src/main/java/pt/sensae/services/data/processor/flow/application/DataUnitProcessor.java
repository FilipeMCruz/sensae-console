package pt.sensae.services.data.processor.flow.application;

import com.fasterxml.jackson.databind.node.ObjectNode;
import pt.sensae.services.data.processor.flow.domain.SensorTypeId;
import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sharespot.iot.core.keys.RoutingKeys;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class DataUnitProcessor {

    @Inject
    DataProcessorExecutor mapper;

    @Inject
    RoutingKeysProvider provider;

    @Inject
    DataUnitPublisher publisher;

    public void publish(MessageConsumed<ObjectNode, DataRoutingKeys> message) {
        message.toSupplied(this::inToOutData, this::inToOutKeys).ifPresent(publisher::next);
    }

    private Optional<DataUnitDTO> inToOutData(ObjectNode node, DataRoutingKeys keys) {
        return mapper.inToOut(node, SensorTypeId.of(keys.sensorTypeId.details()));
    }

    private Optional<DataRoutingKeys> inToOutKeys(DataUnitDTO data, DataRoutingKeys keys) {
        return provider.getBuilder(RoutingKeysBuilderOptions.SUPPLIER).withUpdated(data).from(keys);
    }
}
