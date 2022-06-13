package pt.sensae.services.fleet.management.backend.infrastructure.endpoint.graphql.handlers.subscriptions;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsSubscription;
import com.netflix.graphql.dgs.InputArgument;
import org.reactivestreams.Publisher;
import pt.sensae.services.fleet.management.backend.application.GPSDataPublisher;
import pt.sensae.services.fleet.management.backend.domain.model.livedata.SensorData;
import pt.sensae.services.fleet.management.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;

@DgsComponent
public class ContentFilteredGPSSensorDataChangesSubscription {

    private final GPSDataPublisher publisher;

    public ContentFilteredGPSSensorDataChangesSubscription(GPSDataPublisher publisher) {
        this.publisher = publisher;
    }

    @DgsSubscription
    public Publisher<SensorData> locationByContent(@InputArgument("content") String content, @InputArgument("Authorization") String auth) {
        return publisher.getContentFilteredPublisher(content, AuthMiddleware.buildAccessToken(auth));
    }
}
