package sharespot.services.devicerecordsbackend.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.routing.MessageConsumed;
import pt.sharespot.iot.core.routing.MessageSupplied;
import pt.sharespot.iot.core.routing.keys.*;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class SensorDataHandlerService {

    private FluxSink<MessageSupplied<ProcessedSensorDataDTO>> dataStream;

    private ConnectableFlux<MessageSupplied<ProcessedSensorDataDTO>> dataPublisher;

    private final RecordAppenderService appender;
    private final RoutingKeysProvider provider;

    public SensorDataHandlerService(RecordAppenderService appender, RoutingKeysProvider provider) {
        this.appender = appender;
        this.provider = provider;
    }

    @PostConstruct
    public void init() {
        Flux<MessageSupplied<ProcessedSensorDataDTO>> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<MessageSupplied<ProcessedSensorDataDTO>> getSinglePublisher() {
        return dataPublisher;
    }

    public void publish(MessageConsumed<ProcessedSensorDataDTO> message) {
        message.toSupplied(this::inToOutData, this::inToOutKeys).ifPresent(dataStream::next);
    }

    private Optional<ProcessedSensorDataDTO> inToOutData(ProcessedSensorDataDTO node, RoutingKeys keys) {
        var deviceWithSubDevices = appender.tryToAppend(node);
        deviceWithSubDevices.sensors().forEach(sub -> this.publishSubSensor(sub, keys));
        return Optional.ofNullable(deviceWithSubDevices.controller());
    }

    public void publishSubSensor(ProcessedSensorDataDTO data, RoutingKeys keys) {
        subSensorInToOutKeys(data, keys)
                .map(sub -> MessageSupplied.create(data, sub))
                .ifPresent(dataStream::next);
    }

    private Optional<RoutingKeys> inToOutKeys(ProcessedSensorDataDTO data, RoutingKeys keys) {
        return provider.getBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withUpdated(data)
                .withRecords(RecordsOptions.WITH_RECORDS)
                .from(keys);
    }

    private Optional<RoutingKeys> subSensorInToOutKeys(ProcessedSensorDataDTO data, RoutingKeys keys) {
        return provider.getBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withUpdated(data)
                .withRecords(RecordsOptions.UNIDENTIFIED_RECORDS)
                .withOwnership(DomainOwnershipOptions.UNIDENTIFIED_DOMAIN_OWNERSHIP)
                .withLegitimacyType(DataLegitimacyOptions.UNKNOWN)
                .from(keys);
    }
}
