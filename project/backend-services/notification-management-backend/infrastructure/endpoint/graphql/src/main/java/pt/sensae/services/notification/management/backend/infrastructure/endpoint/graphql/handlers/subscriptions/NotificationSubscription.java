package pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.handlers.subscriptions;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsSubscription;
import com.netflix.graphql.dgs.InputArgument;
import org.reactivestreams.Publisher;
import pt.sensae.services.notification.management.backend.application.notification.model.NotificationDTO;
import pt.sensae.services.notification.management.backend.application.notification.service.UINotificationPublisher;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;

@DgsComponent
public class NotificationSubscription {

    private final UINotificationPublisher publisher;

    public NotificationSubscription(UINotificationPublisher publisher) {
        this.publisher = publisher;
    }

    @DgsSubscription
    public Publisher<NotificationDTO> alerts(@InputArgument("Authorization") String auth) {
        return publisher.getSinglePublisher(AuthMiddleware.buildAccessToken(auth));
    }
}
