package pt.sensae.services.notification.management.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.notification.management.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.notification.management.backend.application.auth.TokenExtractor;
import pt.sensae.services.notification.management.backend.application.auth.UnauthorizedException;
import pt.sensae.services.notification.management.backend.domain.FullNotification;
import pt.sensae.services.notification.management.backend.domain.adressee.AddresseeId;
import pt.sensae.services.notification.management.backend.domain.notification.Notification;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.annotation.PostConstruct;
import java.util.function.Predicate;

@Service
public class UINotificationPublisher {

    private FluxSink<FullNotification> dataStream;

    private ConnectableFlux<FullNotification> dataPublisher;

    private final TokenExtractor authHandler;

    public UINotificationPublisher(TokenExtractor authHandler) {
        this.authHandler = authHandler;
    }

    @PostConstruct
    public void init() {
        Flux<FullNotification> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<Notification> getSinglePublisher(AccessTokenDTO claims) {
        return dataPublisher.filter(byTenantId(claims))
                .map(FullNotification::notification);
    }

    public void send(FullNotification notification) {
        dataStream.next(notification);
    }

    private Predicate<FullNotification> byTenantId(AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("alert_management:live_data:read"))
            throw new UnauthorizedException("No Permissions");

        return s -> s.recipients().stream().anyMatch(r -> r.id().equals(AddresseeId.of(extract.oid)));
    }
}
