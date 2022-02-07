package sharespot.services.fleetmanagementbackend.infrastructure.endpoint.graphql.handlers.subscriptions;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsSubscription;
import com.netflix.graphql.dgs.InputArgument;
import org.reactivestreams.Publisher;
import sharespot.services.fleetmanagementbackend.application.GPSDataPublisher;
import sharespot.services.fleetmanagementbackend.domain.model.livedata.SensorData;

@DgsComponent
public class ContentFilteredGPSSensorDataChangesSubscription {

    private final GPSDataPublisher publisher;

    public ContentFilteredGPSSensorDataChangesSubscription(GPSDataPublisher publisher) {
        this.publisher = publisher;
    }

    @DgsSubscription
    public Publisher<SensorData> locationByContent(@InputArgument("content") String content) {
        return publisher.getContentFilteredPublisher(content);
    }
}
