package sharespot.services.locationtrackingbackend.infrastructure.endpoint.graphql.subscriptions;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsSubscription;
import com.netflix.graphql.dgs.InputArgument;
import org.reactivestreams.Publisher;
import sharespot.services.locationtrackingbackend.application.GPSDataPublisher;
import sharespot.services.locationtrackingbackend.application.OutSensorData;

@DgsComponent
public class ContentFilteredGPSSensorDataChangesSubscription {

    private final GPSDataPublisher publisher;

    public ContentFilteredGPSSensorDataChangesSubscription(GPSDataPublisher publisher) {
        this.publisher = publisher;
    }

    @DgsSubscription
    public Publisher<OutSensorData> locationByContent(@InputArgument("content") String content) {
        return publisher.getContentFilteredPublisher(content);
    }
}
