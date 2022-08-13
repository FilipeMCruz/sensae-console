package pt.sensae.services.data.processor.flow.application;

import com.fasterxml.jackson.databind.node.ObjectNode;
import pt.sensae.services.data.processor.flow.domain.SensorTypeId;
import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;

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

    public void publish(MessageConsumed<ObjectNode, SensorRoutingKeys> message) {
        message.toSupplied(this::inToOutData, this::inToOutKeys).ifPresent(publisher::next);
    }

    private Optional<SensorDataDTO> inToOutData(ObjectNode node, SensorRoutingKeys keys) {
        return mapper.inToOut(node, SensorTypeId.of(keys.sensorTypeId.details()));
    }

    private Optional<SensorRoutingKeys> inToOutKeys(SensorDataDTO data, SensorRoutingKeys keys) {
        return provider.getBuilder(RoutingKeysBuilderOptions.SUPPLIER).withUpdated(data).from(keys);
    }
}
