package sharespot.services.devicerecordsbackend.application;

import org.springframework.stereotype.Service;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import sharespot.services.devicerecordsbackend.domain.model.message.*;

import javax.annotation.PostConstruct;

@Service
public class SensorDataHandlerService {

    private FluxSink<MessageSupplied<ProcessedSensorDataWithRecordDTO>> dataStream;

    private ConnectableFlux<MessageSupplied<ProcessedSensorDataWithRecordDTO>> dataPublisher;

    private final RecordAppenderService appender;

    public SensorDataHandlerService(RecordAppenderService appender) {
        this.appender = appender;
    }

    @PostConstruct
    public void init() {
        Flux<MessageSupplied<ProcessedSensorDataWithRecordDTO>> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<MessageSupplied<ProcessedSensorDataWithRecordDTO>> getSinglePublisher() {
        return dataPublisher;
    }

    public void publish(MessageConsumed<? extends ProcessedSensorDataDTO> message) {

        var dataWithRecordDTO = appender.tryToAppend(message.data);

        var routingKeys = RoutingKeys.builder(RoutingKeysBuilderOptions.SUPPLIER)
                .keepInfoType()
                .keepSensorTypeId()
                .keepChannel()
                .withRecords(RecordsOptions.WITH_RECORDS)
                .withGps(dataWithRecordDTO.hasGpsData() ? GPSDataOptions.WITH_GPS_DATA : GPSDataOptions.WITHOUT_GPS_DATA)
                .withTempC(dataWithRecordDTO.hasTempCData() ? TempCDataOptions.WITH_TEMPC_DATA : TempCDataOptions.WITHOUT_TEMPC_DATA)
                .from(message.routingKeys);

        routingKeys.ifPresent(keys -> dataStream.next(new MessageSupplied<>(keys, appender.tryToAppend(message.data))));
    }
}
