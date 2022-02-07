package sharespot.services.fleetmanagementbackend.infrastructure.endpoint.graphql.handlers.subscriptions;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsSubscription;
import org.reactivestreams.Publisher;
import sharespot.services.fleetmanagementbackend.application.GPSDataPublisher;
import sharespot.services.fleetmanagementbackend.domain.model.livedata.SensorData;

@DgsComponent
public class AllGPSSensorsDataChangesSubscription {

    private final GPSDataPublisher publisher;

    public AllGPSSensorsDataChangesSubscription(GPSDataPublisher publisher) {
        this.publisher = publisher;
    }

    @DgsSubscription
    public Publisher<SensorData> locations() {
        return publisher.getGeneralPublisher();
    }
}
