package pt.sharespot.services.locationtrackingbackend.infrastructure.endpoint.graphql.subscriptions;

import com.coxautodev.graphql.tools.GraphQLSubscriptionResolver;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import pt.sharespot.services.locationtrackingbackend.application.publishers.GPSDataPublisher;
import pt.sharespot.services.locationtrackingbackend.domain.sensor.gps.GPSData;

import java.util.UUID;

@Component
public class SingleGPSSensorDataChangesSubscription implements GraphQLSubscriptionResolver {

    private final GPSDataPublisher publisher;

    public SingleGPSSensorDataChangesSubscription(GPSDataPublisher publisher) {
        this.publisher = publisher;
    }

    public Publisher<GPSData> location(UUID id) {
        return publisher.getSinglePublisher(id);
    }
}
