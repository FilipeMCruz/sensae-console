package pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.handlers.queries;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.notification.management.backend.application.addressee.model.AddresseeDTO;
import pt.sensae.services.notification.management.backend.application.addressee.service.AddresseeCollector;
import pt.sensae.services.notification.management.backend.application.notification.model.NotificationDTO;
import pt.sensae.services.notification.management.backend.application.notification.service.NotificationCollector;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.model.NotificationHistoryQueryDTOImpl;

import java.util.Set;
import java.util.stream.Collectors;

@DgsComponent
public class NotificationHistoryQuery {

    private final NotificationCollector collector;

    public NotificationHistoryQuery(NotificationCollector collector) {
        this.collector = collector;
    }

    @DgsQuery
    public Set<NotificationDTO> history(@InputArgument("filters") NotificationHistoryQueryDTOImpl filters, @RequestHeader("Authorization") String auth) {
        return this.collector.fetch(filters, AuthMiddleware.buildAccessToken(auth)).collect(Collectors.toSet());
    }
}
