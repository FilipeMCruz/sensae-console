package pt.sharespot.services.locationtrackingbackend.infrastructure.endpoint.graphql.subscriptions;

import org.reactivestreams.Publisher;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import pt.sharespot.services.locationtrackingbackend.application.publishers.GPSDataPublisher;
import pt.sharespot.services.locationtrackingbackend.domain.sensor.gps.GPSData;

import java.util.UUID;

@Controller
public class SingleGPSSensorDataChangesSubscription {

    private final GPSDataPublisher publisher;

    public SingleGPSSensorDataChangesSubscription(GPSDataPublisher publisher) {
        this.publisher = publisher;
    }

    @SubscriptionMapping
    public Publisher<GPSData> location(@Argument("id") UUID id) {
        return publisher.getSinglePublisher(id);
    }
}
