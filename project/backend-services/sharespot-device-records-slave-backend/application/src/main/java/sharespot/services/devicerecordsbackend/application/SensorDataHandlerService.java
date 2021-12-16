package sharespot.services.devicerecordsbackend.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.routing.MessageConsumed;
import pt.sharespot.iot.core.routing.MessageSupplied;
import pt.sharespot.iot.core.routing.keys.GPSDataOptions;
import pt.sharespot.iot.core.routing.keys.RecordsOptions;
import pt.sharespot.iot.core.routing.keys.RoutingKeys;
import pt.sharespot.iot.core.routing.keys.TempCDataOptions;
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

    public SensorDataHandlerService(RecordAppenderService appender) {
        this.appender = appender;
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

        var routingKeys = RoutingKeys.supplierBuilder("devicerecordsslave", "devicerecordsslave")
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
