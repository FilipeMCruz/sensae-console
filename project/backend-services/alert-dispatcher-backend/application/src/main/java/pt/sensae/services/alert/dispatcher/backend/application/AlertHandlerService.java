package pt.sensae.services.alert.dispatcher.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.alert.dispatcher.backend.domain.AlertMessage;
import pt.sharespot.iot.core.alert.model.AlertDTO;
import pt.sharespot.iot.core.alert.routing.keys.AlertCategoryOptions;
import pt.sharespot.iot.core.alert.routing.keys.AlertSeverityOptions;
import pt.sharespot.iot.core.keys.ContainerTypeOptions;
import pt.sharespot.iot.core.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class AlertHandlerService {

    private FluxSink<AlertMessage> dataStream;

    private ConnectableFlux<AlertMessage> dataPublisher;

    private final RoutingKeysProvider routingKeysProvider;

    public AlertHandlerService(RoutingKeysProvider routingKeysProvider) {
        this.routingKeysProvider = routingKeysProvider;
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

    public void publish(AlertDTO alert) {
        buildAlertRoutingKeys(alert).ifPresent(dataStream::next);
    }

    private Optional<AlertMessage> buildAlertRoutingKeys(AlertDTO alert) {
        var keys = routingKeysProvider.getAlertBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withLegitimacyType(DataLegitimacyOptions.UNDETERMINED)
                .withContainerType(ContainerTypeOptions.ALERT_DISPATCHER)
                .withSeverityType(AlertSeverityOptions.extract(alert.level))
                .withCategoryType(AlertCategoryOptions.of(alert.category))
                .build();
        return keys.map(routingKeys -> new AlertMessage(alert, routingKeys));
    }
}
