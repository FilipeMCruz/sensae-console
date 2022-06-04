package pt.sensae.services.notification.dispatcher.backend.application.notification;

import org.springframework.stereotype.Service;
import pt.sensae.services.notification.dispatcher.backend.domain.notification.Notification;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.annotation.PostConstruct;

@Service
public class NotificationPublisher {

    private FluxSink<Notification> dataStream;

    private ConnectableFlux<Notification> dataPublisher;

    @PostConstruct
    public void init() {
        Flux<Notification> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<Notification> getSinglePublisher() {
        return dataPublisher;
    }

    public void publish(Notification message) {
        dataStream.next(message);
    }
}
