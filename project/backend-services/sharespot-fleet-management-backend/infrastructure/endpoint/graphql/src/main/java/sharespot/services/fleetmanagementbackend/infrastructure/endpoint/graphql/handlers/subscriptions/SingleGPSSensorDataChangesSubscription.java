package sharespot.services.fleetmanagementbackend.infrastructure.endpoint.graphql.handlers.subscriptions;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsSubscription;
import com.netflix.graphql.dgs.InputArgument;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.RequestHeader;
import sharespot.services.fleetmanagementbackend.application.GPSDataPublisher;
import sharespot.services.fleetmanagementbackend.domain.model.livedata.SensorData;
import sharespot.services.fleetmanagementbackend.infrastructure.endpoint.graphql.auth.AuthMiddleware;

import java.util.List;

@DgsComponent
public class SingleGPSSensorDataChangesSubscription {

    private final GPSDataPublisher publisher;

    public SingleGPSSensorDataChangesSubscription(GPSDataPublisher publisher) {
        this.publisher = publisher;
    }

    @DgsSubscription
    public Publisher<SensorData> location(@InputArgument("devices") List<String> ids, @RequestHeader("Authorization") String auth) {
        return publisher.getSinglePublisher(ids, AuthMiddleware.buildAccessToken(auth));
    }
}
