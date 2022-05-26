package sharespot.services.identitymanagementslavebackend.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.alert.model.AlertDTO;
import pt.sharespot.iot.core.alert.routing.keys.AlertCategoryOptions;
import pt.sharespot.iot.core.alert.routing.keys.AlertRoutingKeys;
import pt.sharespot.iot.core.alert.routing.keys.AlertSeverityOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.OwnershipOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.routing.MessageConsumed;
import pt.sharespot.iot.core.sensor.routing.MessageSupplied;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.alert.AlertMessage;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class AlertPublisherService {

    private FluxSink<AlertMessage> dataStream;

    private ConnectableFlux<AlertMessage> dataPublisher;

    private final DomainAppenderService appender;
    private final RoutingKeysProvider provider;

    public AlertPublisherService(DomainAppenderService appender, RoutingKeysProvider provider) {
        this.appender = appender;
        this.provider = provider;
    }

    @PostConstruct
    public void init() {
        Flux<AlertMessage> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<AlertMessage> getSinglePublisher() {
        return dataPublisher;
    }

    public void publish(AlertDTO message) {
        appender.tryToAppend(message).flatMap(this::inToOutKeys).ifPresent(dataStream::next);
    }

    private Optional<AlertMessage> inToOutKeys(AlertDTO alert) {
        var keys = provider.getAlertTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withOwnershipType(OwnershipOptions.WITH_DOMAIN_OWNERSHIP)
                .withContainerType(ContainerTypeOptions.IDENTITY_MANAGEMENT)
                .withSeverityType(AlertSeverityOptions.extract(alert.level))
                .withCategoryType(AlertCategoryOptions.of(alert.category))
                .build();
        return keys.map(routingKeys -> new AlertMessage(alert, routingKeys));
    }
}
