package pt.sensae.services.data.validator.application;


import pt.sharespot.iot.core.data.model.DataUnitDTO;
import pt.sharespot.iot.core.data.routing.keys.DataRoutingKeys;
import pt.sharespot.iot.core.keys.MessageConsumed;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class DataUnitHandlerService {

    @Inject
    DataValidatorService service;

    @Inject
    EventPublisher dataStream;

    public void publish(MessageConsumed<DataUnitDTO, DataRoutingKeys> message) {
        message.toSupplied(this::inToOutData, this::inToOutKeys).ifPresent(dataStream::next);
    }

    private Optional<DataUnitDTO> inToOutData(DataUnitDTO node, DataRoutingKeys keys) {
        return Optional.of(node);
    }

    private Optional<DataRoutingKeys> inToOutKeys(DataUnitDTO data, DataRoutingKeys keys) {
        return service.decide(data, keys);
    }
}
