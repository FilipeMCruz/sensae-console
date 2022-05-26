package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.handlers.subscriptions;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsSubscription;
import com.netflix.graphql.dgs.InputArgument;
import org.reactivestreams.Publisher;
import pt.sensae.services.smart.irrigation.backend.application.services.data.SensorDataPublisher;
import pt.sensae.services.smart.irrigation.backend.application.model.data.SensorReadingDTO;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data.LiveDataFilterDTOImpl;

@DgsComponent
public class SensorDataSubscription {

    private final SensorDataPublisher publisher;

    public SensorDataSubscription(SensorDataPublisher publisher) {
        this.publisher = publisher;
    }

    @DgsSubscription
    public Publisher<SensorReadingDTO> data(@InputArgument("filters") LiveDataFilterDTOImpl filters, @InputArgument("Authorization") String auth) {
        return publisher.getFilteredPublisher(filters, AuthMiddleware.buildAccessToken(auth));
    }
}
