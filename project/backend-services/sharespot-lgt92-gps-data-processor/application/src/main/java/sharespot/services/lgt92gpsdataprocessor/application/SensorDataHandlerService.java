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
        mapper.inToOut(message.data).ifPresent(dto ->
                RoutingKeys.builder(RoutingKeysBuilderOptions.SUPPLIER)
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
