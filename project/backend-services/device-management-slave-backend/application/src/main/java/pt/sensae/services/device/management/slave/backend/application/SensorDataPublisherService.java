package pt.sensae.services.device.management.slave.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.device.management.slave.backend.domain.model.device.DeviceId;
import pt.sensae.services.device.management.slave.backend.domainservices.UnhandledSensorDataCache;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.model.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.routing.MessageConsumed;
import pt.sharespot.iot.core.sensor.routing.MessageSupplied;
import pt.sharespot.iot.core.sensor.routing.keys.DomainOwnershipOptions;
import pt.sharespot.iot.core.sensor.routing.keys.RecordsOptions;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class SensorDataPublisherService {

    private FluxSink<MessageSupplied<ProcessedSensorDataDTO>> dataStream;

    private ConnectableFlux<MessageSupplied<ProcessedSensorDataDTO>> dataPublisher;

    private final RecordAppenderService appender;

    private final RoutingKeysProvider provider;

    public SensorDataPublisherService(RecordAppenderService appender, RoutingKeysProvider provider) {
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

    private Optional<ProcessedSensorDataDTO> inToOutData(ProcessedSensorDataDTO node, SensorRoutingKeys keys) {
        return appender.tryToAppend(node).map(device -> {
            device.sensors().forEach(sub -> this.publishSubSensor(sub, keys));
            return device.controller();
        });
    }

    public void publishSubSensor(ProcessedSensorDataDTO data, SensorRoutingKeys keys) {
        subSensorInToOutKeys(data, keys).map(sub -> MessageSupplied.create(data, sub)).ifPresent(dataStream::next);
    }

    private Optional<SensorRoutingKeys> inToOutKeys(ProcessedSensorDataDTO data, SensorRoutingKeys keys) {
        return provider.getSensorTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withUpdated(data)
                .withRecords(RecordsOptions.WITH_RECORDS)
                .from(keys);
    }

    private Optional<SensorRoutingKeys> subSensorInToOutKeys(ProcessedSensorDataDTO data, SensorRoutingKeys keys) {
        return provider.getSensorTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withUpdated(data)
                .withRecords(RecordsOptions.UNIDENTIFIED_RECORDS)
                .withOwnership(DomainOwnershipOptions.UNIDENTIFIED_DOMAIN_OWNERSHIP)
                .from(keys);
    }
}
