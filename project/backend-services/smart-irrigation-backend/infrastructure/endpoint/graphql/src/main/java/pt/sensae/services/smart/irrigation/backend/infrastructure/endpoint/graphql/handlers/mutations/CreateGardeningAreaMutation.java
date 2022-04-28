package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.handlers.mutations;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.smart.irrigation.backend.application.services.garden.CreateGardeningAreaService;
import pt.sensae.services.smart.irrigation.backend.application.model.garden.GardeningAreaDTO;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.garden.CreateGardeningAreaCommandDTOImpl;

@DgsComponent
public class CreateGardeningAreaMutation {

    private final CreateGardeningAreaService service;

    public CreateGardeningAreaMutation(CreateGardeningAreaService service) {
        this.service = service;
    }

    @DgsMutation
    public GardeningAreaDTO createGarden(@InputArgument("instructions") CreateGardeningAreaCommandDTOImpl command, @RequestHeader("Authorization") String auth) {
        return service.create(command, AuthMiddleware.buildAccessToken(auth));
    }
}
