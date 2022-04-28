package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.handlers.mutations;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.smart.irrigation.backend.application.services.command.ValveSwitcherService;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.device.ValvesToSwitchDTOImpl;

@DgsComponent
public class SwitchValveStatusMutation {

    private final ValveSwitcherService service;

    public SwitchValveStatusMutation(ValveSwitcherService service) {
        this.service = service;
    }

    @DgsMutation
    public Boolean switchValve(@InputArgument("instructions") ValvesToSwitchDTOImpl valves, @RequestHeader("Authorization") String auth) {
        return service.switchValve(valves.id, AuthMiddleware.buildAccessToken(auth));
    }
}
