package sharespot.services.devicerecordsbackend.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.routing.MessageConsumed;
import pt.sharespot.iot.core.routing.MessageSupplied;
import pt.sharespot.iot.core.routing.keys.RecordsOptions;
import pt.sharespot.iot.core.routing.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
import pt.sharespot.iot.core.sensor.properties.PropertyName;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.annotation.PostConstruct;

@Service
public class SensorDataHandlerService {

    private FluxSink<MessageSupplied<ProcessedSensorDataWithRecordsDTO>> dataStream;

    private ConnectableFlux<MessageSupplied<ProcessedSensorDataWithRecordsDTO>> dataPublisher;

    private final RecordAppenderService appender;
    private final RoutingKeysProvider provider;

    public SensorDataHandlerService(RecordAppenderService appender, RoutingKeysProvider provider) {
        this.appender = appender;
        this.provider = provider;
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

        var hasRecords = dataWithRecordDTO.hasProperty(PropertyName.DEVICE_RECORDS);
        
        var routingKeys = provider.getBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withUpdated(dataWithRecordDTO)
                .withRecords(hasRecords ? RecordsOptions.WITH_RECORDS : RecordsOptions.WITHOUT_RECORDS)
                .from(message.routingKeys);

        routingKeys.ifPresent(keys -> dataStream.next(new MessageSupplied<>(keys, appender.tryToAppend(message.data))));
    }
}
