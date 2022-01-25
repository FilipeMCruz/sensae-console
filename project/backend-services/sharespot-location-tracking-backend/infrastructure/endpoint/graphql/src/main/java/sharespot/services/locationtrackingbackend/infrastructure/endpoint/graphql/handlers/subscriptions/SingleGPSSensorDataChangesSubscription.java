package sharespot.services.locationtrackingbackend.infrastructure.endpoint.graphql.handlers.subscriptions;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsSubscription;
import com.netflix.graphql.dgs.InputArgument;
import org.reactivestreams.Publisher;
import sharespot.services.locationtrackingbackend.application.GPSDataPublisher;
import sharespot.services.locationtrackingbackend.domain.model.livedata.SensorData;

import java.util.List;

@DgsComponent
public class SingleGPSSensorDataChangesSubscription {

    private final GPSDataPublisher publisher;

    public SingleGPSSensorDataChangesSubscription(GPSDataPublisher publisher) {
        this.publisher = publisher;
    }

    @DgsSubscription
    public Publisher<SensorData> location(@InputArgument("devices") List<String> ids) {
        return publisher.getSinglePublisher(ids);
    }
}
