package pt.sharespot.services.locationtrackingbackend.infrastructure.endpoint.graphql.subscriptions;

import org.reactivestreams.Publisher;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import pt.sharespot.services.locationtrackingbackend.application.publishers.GPSDataPublisher;
import pt.sharespot.services.locationtrackingbackend.domain.sensor.gps.GPSData;

@Controller
public class AllGPSSensorsDataChangesSubscription {

    private final GPSDataPublisher publisher;

    public AllGPSSensorsDataChangesSubscription(GPSDataPublisher publisher) {
        this.publisher = publisher;
    }

    @SubscriptionMapping
    public Publisher<GPSData> locations() {
        return publisher.getGeneralPublisher();
    }
}
