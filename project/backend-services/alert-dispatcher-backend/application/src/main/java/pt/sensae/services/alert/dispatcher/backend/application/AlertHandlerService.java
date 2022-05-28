package pt.sensae.services.alert.dispatcher.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.alert.dispatcher.backend.domain.AlertMessage;
import pt.sharespot.iot.core.alert.model.AlertDTO;
import pt.sharespot.iot.core.alert.model.AlertDispatcherService;
import pt.sharespot.iot.core.alert.routing.keys.AlertCategoryOptions;
import pt.sharespot.iot.core.alert.routing.keys.AlertRoutingKeys;
import pt.sharespot.iot.core.alert.routing.keys.AlertSeverityOptions;
import pt.sharespot.iot.core.alert.routing.keys.AlertSubCategoryOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.MessageSupplied;
import pt.sharespot.iot.core.keys.OwnershipOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class AlertHandlerService implements AlertDispatcherService {

    private FluxSink<MessageSupplied<AlertDTO, AlertRoutingKeys>> dataStream;

    private ConnectableFlux<MessageSupplied<AlertDTO, AlertRoutingKeys>> dataPublisher;

    private final RoutingKeysProvider routingKeysProvider;

    public AlertHandlerService(RoutingKeysProvider routingKeysProvider) {
        this.routingKeysProvider = routingKeysProvider;
    }

    @PostConstruct
    public void init() {
        Flux<MessageSupplied<AlertDTO, AlertRoutingKeys>> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<MessageSupplied<AlertDTO, AlertRoutingKeys>> getSinglePublisher() {
        return dataPublisher;
    }

    public void publish(AlertDTO alert) {
        buildAlertRoutingKeys(alert).ifPresent(dataStream::next);
    }

    private Optional<MessageSupplied<AlertDTO, AlertRoutingKeys>> buildAlertRoutingKeys(AlertDTO alert) {
        var keys = routingKeysProvider.getAlertBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withOwnershipType(OwnershipOptions.UNIDENTIFIED_DOMAIN_OWNERSHIP)
                .withContainerType(ContainerTypeOptions.ALERT_DISPATCHER)
                .withSeverityType(AlertSeverityOptions.extract(alert.level))
                .withCategoryType(AlertCategoryOptions.of(alert.category))
                .withSubCategoryType(AlertSubCategoryOptions.of(alert.subCategory))
                .build();
        return keys.map(routingKeys -> MessageSupplied.create(alert, routingKeys));
    }
}
