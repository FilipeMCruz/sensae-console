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
public class DeviceFilteredSensorDataSubscription {

    private final SensorDataPublisher publisher;

    public DeviceFilteredSensorDataSubscription(SensorDataPublisher publisher) {
        this.publisher = publisher;
    }

    @DgsSubscription
    public Publisher<SensorDataDTO> dataByDevices(@InputArgument("devices") List<String> ids, @InputArgument("Authorization") String auth) {
        return publisher.getDeviceFilteredPublisher(ids.stream(), AuthMiddleware.buildAccessToken(auth));
    }
}
