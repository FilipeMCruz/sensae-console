package pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.handlers.queries;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.notification.management.backend.application.addressee.model.AddresseeDTO;
import pt.sensae.services.notification.management.backend.application.addressee.service.AddresseeCollector;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.model.AddresseeConfigUpdateCommandDTOImpl;

@DgsComponent
public class AddresseeConfigQuery {

    private final AddresseeCollector collector;

    public AddresseeConfigQuery(AddresseeCollector collector) {
        this.collector = collector;
    }

    @DgsQuery
    public AddresseeDTO config(@RequestHeader("Authorization") String auth) {
        return this.collector.fetch(AuthMiddleware.buildAccessToken(auth));
    }
}
