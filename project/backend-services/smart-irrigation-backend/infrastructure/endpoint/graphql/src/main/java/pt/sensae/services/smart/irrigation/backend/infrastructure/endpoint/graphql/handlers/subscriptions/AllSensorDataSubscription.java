package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.handlers.subscriptions;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsSubscription;
import com.netflix.graphql.dgs.InputArgument;
import org.reactivestreams.Publisher;
import pt.sensae.services.smart.irrigation.backend.application.SensorDataDTO;
import pt.sensae.services.smart.irrigation.backend.application.SensorDataPublisher;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;

@DgsComponent
public class AllSensorDataSubscription {

    private final SensorDataPublisher publisher;

    public AllSensorDataSubscription(SensorDataPublisher publisher) {
        this.publisher = publisher;
    }

    @DgsSubscription
    public Publisher<SensorDataDTO> data(@InputArgument("Authorization") String auth) {
        return publisher.getGeneralPublisher(AuthMiddleware.buildAccessToken(auth));
    }

    @DgsSubscription
    public Publisher<SensorDataDTO> dataA() {
        return publisher.getPublisher();
    }
}
