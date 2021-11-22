package sharespot.services.locationtrackingbackend.infrastructure.endpoint.graphql.subscriptions;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsSubscription;
import com.netflix.graphql.dgs.InputArgument;
import org.reactivestreams.Publisher;
import sharespot.services.locationtrackingbackend.application.publishers.GPSDataPublisher;
import sharespot.services.locationtrackingbackend.domain.sensor.gps.SensorData;

import java.util.UUID;

@DgsComponent
public class SingleGPSSensorDataChangesSubscription {

    private final GPSDataPublisher publisher;

    public SingleGPSSensorDataChangesSubscription(GPSDataPublisher publisher) {
        this.publisher = publisher;
    }

    @DgsSubscription
    public Publisher<SensorData> location(@InputArgument("deviceId") String id) {
        return publisher.getSinglePublisher(UUID.fromString(id));
    }
}
