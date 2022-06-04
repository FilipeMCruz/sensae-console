package pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.handlers.mutations;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.notification.management.backend.application.addressee.model.AddresseeDTO;
import pt.sensae.services.notification.management.backend.application.addressee.service.AddresseeArchiver;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.model.AddresseeConfigUpdateCommandDTOImpl;

@DgsComponent
public class AddresseeConfigMutation {

    private final AddresseeArchiver archiver;

    public AddresseeConfigMutation(AddresseeArchiver archiver) {
        this.archiver = archiver;
    }
    
    @DgsMutation
    public AddresseeDTO config(@InputArgument("instructions") AddresseeConfigUpdateCommandDTOImpl command, @RequestHeader("Authorization") String auth) {
        return this.archiver.edit(command, AuthMiddleware.buildAccessToken(auth));
    }
}
