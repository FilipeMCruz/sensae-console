package pt.sensae.services.fleet.management.backend.infrastructure.endpoint.graphql.handlers.subscriptions;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsSubscription;
import com.netflix.graphql.dgs.InputArgument;
import org.reactivestreams.Publisher;
import pt.sensae.services.fleet.management.backend.application.GPSDataPublisher;
import pt.sensae.services.fleet.management.backend.domain.model.livedata.SensorData;
import pt.sensae.services.fleet.management.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;

@DgsComponent
public class AllGPSSensorsDataChangesSubscription {

    private final GPSDataPublisher publisher;

    public AllGPSSensorsDataChangesSubscription(GPSDataPublisher publisher) {
        this.publisher = publisher;
    }

    @DgsSubscription
    public Publisher<SensorData> locations(@InputArgument("Authorization") String auth) {
        return publisher.getGeneralPublisher(AuthMiddleware.buildAccessToken(auth));
    }
}
