package pt.sensae.services.fleet.management.backend.infrastructure.endpoint.graphql.handlers.subscriptions;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsSubscription;
import com.netflix.graphql.dgs.InputArgument;
import org.reactivestreams.Publisher;
import pt.sensae.services.fleet.management.backend.application.GPSDataPublisher;
import pt.sensae.services.fleet.management.backend.domain.model.livedata.SensorData;
import pt.sensae.services.fleet.management.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;

import java.util.List;

@DgsComponent
public class SingleGPSSensorDataChangesSubscription {

    private final GPSDataPublisher publisher;

    public SingleGPSSensorDataChangesSubscription(GPSDataPublisher publisher) {
        this.publisher = publisher;
    }

    @DgsSubscription
    public Publisher<SensorData> location(@InputArgument("devices") List<String> ids, @InputArgument("Authorization") String auth) {
        return publisher.getSinglePublisher(ids, AuthMiddleware.buildAccessToken(auth));
    }
}
