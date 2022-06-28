package pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.handlers.mutations;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.notification.management.backend.application.addressee.model.AddresseeDTO;
import pt.sensae.services.notification.management.backend.application.addressee.service.AddresseeArchiver;
import pt.sensae.services.notification.management.backend.application.notification.model.ReadNotificationDTO;
import pt.sensae.services.notification.management.backend.application.notification.service.NotificationReadRegister;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.model.AddresseeConfigUpdateCommandDTOImpl;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.model.ReadNotificationDTOImpl;

@DgsComponent
public class ReadNotificationMutation {

    private final NotificationReadRegister archiver;

    public ReadNotificationMutation(NotificationReadRegister archiver) {
        this.archiver = archiver;
    }

    @DgsMutation
    public ReadNotificationDTO register(@InputArgument("read") ReadNotificationDTOImpl command, @RequestHeader("Authorization") String auth) {
        return this.archiver.register(command, AuthMiddleware.buildAccessToken(auth));
    }
}
