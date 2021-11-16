package pt.sharespot.services.locationtrackingbackend.infrastructure.endpoint.graphql.subscriptions;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsSubscription;
import org.reactivestreams.Publisher;
import pt.sharespot.services.locationtrackingbackend.application.publishers.GPSDataPublisher;
import pt.sharespot.services.locationtrackingbackend.domain.sensor.gps.GPSData;

@DgsComponent
public class AllGPSSensorsDataChangesSubscription {

    private final GPSDataPublisher publisher;

    public AllGPSSensorsDataChangesSubscription(GPSDataPublisher publisher) {
        this.publisher = publisher;
    }

    @DgsSubscription
    public Publisher<GPSData> locations() {
        return publisher.getGeneralPublisher();
    }
}
