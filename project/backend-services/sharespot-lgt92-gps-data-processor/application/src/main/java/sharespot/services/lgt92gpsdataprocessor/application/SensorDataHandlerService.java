package sharespot.services.lgt92gpsdataprocessor.application;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import sharespot.services.lgt92gpsdataprocessor.application.model.*;

import javax.annotation.PostConstruct;

@Service
public class SensorDataHandlerService {

    private final SensorDataMapper mapper;

    private FluxSink<MessageSupplied<OutSensorDataDTO>> dataStream;

    private ConnectableFlux<MessageSupplied<OutSensorDataDTO>> dataPublisher;

    public SensorDataHandlerService(SensorDataMapper mapper) {
        this.mapper = mapper;
    }

    @PostConstruct
    public void init() {
        Flux<MessageSupplied<OutSensorDataDTO>> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<MessageSupplied<OutSensorDataDTO>> getSinglePublisher() {
        return dataPublisher;
    }

    public void publish(MessageConsumed<ObjectNode> message) {
        var routingKeys = RoutingKeys.builder(RoutingKeysBuilderOptions.SUPPLIER)
                .withInfoType(InfoTypeOptions.PROCESSED)
                .keepSensorTypeId()
                .keepChannel()
                .withRecords(RecordsOptions.WITHOUT_RECORDS)
                .withGps(GPSDataOptions.WITHOUT_GPS_DATA)
                .withTempC(TempCDataOptions.WITHOUT_TEMPC_DATA)
                .from(message.routingKeys);
        
        routingKeys.ifPresent(keys -> mapper.inToOut(message.data)
                .ifPresent(outSensorDataDTO -> dataStream.next(new MessageSupplied<>(keys, outSensorDataDTO))));
    }
}
