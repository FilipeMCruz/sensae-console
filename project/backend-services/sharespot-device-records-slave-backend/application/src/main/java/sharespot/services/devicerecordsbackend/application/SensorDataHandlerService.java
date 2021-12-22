package sharespot.services.devicerecordsbackend.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.routing.MessageConsumed;
import pt.sharespot.iot.core.routing.MessageSupplied;
import pt.sharespot.iot.core.routing.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.routing.keys.RoutingKeysFactory;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.annotation.PostConstruct;

@Service
public class SensorDataHandlerService {

    private FluxSink<MessageSupplied<ProcessedSensorDataWithRecordsDTO>> dataStream;

    private ConnectableFlux<MessageSupplied<ProcessedSensorDataWithRecordsDTO>> dataPublisher;

    private final RecordAppenderService appender;
    private final RoutingKeysFactory factory;

    public SensorDataHandlerService(RecordAppenderService appender, RoutingKeysFactory factory) {
        this.appender = appender;
        this.factory = factory;
    }

    @PostConstruct
    public void init() {
        Flux<MessageSupplied<ProcessedSensorDataWithRecordsDTO>> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<MessageSupplied<ProcessedSensorDataWithRecordsDTO>> getSinglePublisher() {
        return dataPublisher;
    }

    public void publish(MessageConsumed<ProcessedSensorDataDTO> message) {

        var dataWithRecordDTO = appender.tryToAppend(message.data);

        var routingKeys = factory.getBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .keepSensorTypeId()
                .keepChannel()
                .withUpdated(dataWithRecordDTO)
                .from(message.routingKeys);

        routingKeys.ifPresent(keys -> dataStream.next(new MessageSupplied<>(keys, appender.tryToAppend(message.data))));
    }
}
