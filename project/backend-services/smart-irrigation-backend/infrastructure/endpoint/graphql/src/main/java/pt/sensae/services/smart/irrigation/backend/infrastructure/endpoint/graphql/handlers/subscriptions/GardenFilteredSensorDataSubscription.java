package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.handlers.subscriptions;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsSubscription;
import com.netflix.graphql.dgs.InputArgument;
import org.reactivestreams.Publisher;
import pt.sensae.services.smart.irrigation.backend.application.model.SensorDataDTO;
import pt.sensae.services.smart.irrigation.backend.application.SensorDataPublisher;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;

import java.util.List;

@DgsComponent
public class GardenFilteredSensorDataSubscription {

    private final SensorDataPublisher publisher;

    public GardenFilteredSensorDataSubscription(SensorDataPublisher publisher) {
        this.publisher = publisher;
    }

    @DgsSubscription
    public Publisher<SensorDataDTO> dataByGardens(@InputArgument("garden") List<String> ids, @InputArgument("Authorization") String auth) {
        return publisher.getGardenFilteredPublisher(ids.stream(), AuthMiddleware.buildAccessToken(auth));
    }
}
