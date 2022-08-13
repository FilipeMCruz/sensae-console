package pt.sensae.services.data.validator.application;

import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class DataUnitHandlerService {

    @Inject
    DataValidatorService service;

    @Inject
    EventPublisher dataStream;
    
    public void publish(MessageConsumed<SensorDataDTO, SensorRoutingKeys> message) {
        message.toSupplied(this::inToOutData, this::inToOutKeys).ifPresent(dataStream::next);
    }

    private Optional<SensorDataDTO> inToOutData(SensorDataDTO node, SensorRoutingKeys keys) {
        return Optional.of(node);
    }

    private Optional<SensorRoutingKeys> inToOutKeys(SensorDataDTO data, SensorRoutingKeys keys) {
        return service.decide(data, keys);
    }
}
