package sharespot.services.lgt92gpsdataprocessor.application;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.routing.MessageConsumed;
import pt.sharespot.iot.core.routing.MessageSupplied;
import pt.sharespot.iot.core.routing.keys.*;
import pt.sharespot.iot.core.sensor.SensorData;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.annotation.PostConstruct;

@Service
public class SensorDataHandlerService {

    private final SensorDataMapper mapper;

    private FluxSink<MessageSupplied<SensorData>> dataStream;

    private ConnectableFlux<MessageSupplied<SensorData>> dataPublisher;

    public SensorDataHandlerService(SensorDataMapper mapper) {
        this.mapper = mapper;
    }

    @PostConstruct
    public void init() {
        Flux<MessageSupplied<SensorData>> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<MessageSupplied<SensorData>> getSinglePublisher() {
        return dataPublisher;
    }

    public void publish(MessageConsumed<ObjectNode> message) {
        mapper.inToOut(message.data).ifPresent(dto ->
                RoutingKeys.builder("lgt92gpsdataprocessor", "dataprocessor")
                        .withInfoType(InfoTypeOptions.PROCESSED)
                        .keepSensorTypeId()
                        .keepChannel()
                        .withRecords(RecordsOptions.WITHOUT_RECORDS)
                        .withGps(dto.hasGpsData() ? GPSDataOptions.WITH_GPS_DATA : GPSDataOptions.WITHOUT_GPS_DATA)
                        .withTempC(dto.hasTempCData() ? TempCDataOptions.WITH_TEMPC_DATA : TempCDataOptions.WITHOUT_TEMPC_DATA)
                        .from(message.routingKeys)
                        .ifPresent(keys -> dataStream.next(new MessageSupplied<>(keys, dto))));
    }
}
