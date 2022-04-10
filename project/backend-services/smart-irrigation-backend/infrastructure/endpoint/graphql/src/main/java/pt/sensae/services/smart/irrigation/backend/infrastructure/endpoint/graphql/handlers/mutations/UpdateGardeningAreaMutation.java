package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.handlers.mutations;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.smart.irrigation.backend.application.UpdateGardeningAreaService;
import pt.sensae.services.smart.irrigation.backend.application.model.garden.GardeningAreaDTO;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.garden.UpdateGardeningAreaCommandDTOImpl;

@DgsComponent
public class UpdateGardeningAreaMutation {

    private final UpdateGardeningAreaService service;

    public UpdateGardeningAreaMutation(UpdateGardeningAreaService service) {
        this.service = service;
    }

    @DgsMutation
    public GardeningAreaDTO updateGarden(@InputArgument("instructions") UpdateGardeningAreaCommandDTOImpl command, @RequestHeader("Authorization") String auth) {
        return service.update(command, AuthMiddleware.buildAccessToken(auth));
    }
}
