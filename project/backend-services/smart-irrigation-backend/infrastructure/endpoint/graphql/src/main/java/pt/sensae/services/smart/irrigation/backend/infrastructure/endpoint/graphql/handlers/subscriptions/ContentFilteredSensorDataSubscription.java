package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.handlers.subscriptions;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsSubscription;
import com.netflix.graphql.dgs.InputArgument;
import org.reactivestreams.Publisher;
import pt.sensae.services.smart.irrigation.backend.application.SensorDataDTO;
import pt.sensae.services.smart.irrigation.backend.application.SensorDataPublisher;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;

@DgsComponent
public class ContentFilteredSensorDataSubscription {

    private final SensorDataPublisher publisher;

    public ContentFilteredSensorDataSubscription(SensorDataPublisher publisher) {
        this.publisher = publisher;
    }

    @DgsSubscription
    public Publisher<SensorDataDTO> dataByContent(@InputArgument("content") String content, @InputArgument("Authorization") String auth) {
        return publisher.getContentFilteredPublisher(content, AuthMiddleware.buildAccessToken(auth));
    }
}
