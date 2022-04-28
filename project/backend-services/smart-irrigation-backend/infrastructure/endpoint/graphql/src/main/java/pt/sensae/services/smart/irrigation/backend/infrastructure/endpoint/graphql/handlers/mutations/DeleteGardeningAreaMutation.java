package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.handlers.mutations;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.smart.irrigation.backend.application.model.garden.GardeningAreaDTO;
import pt.sensae.services.smart.irrigation.backend.application.services.garden.DeleteGardeningAreaService;
import pt.sensae.services.smart.irrigation.backend.application.services.garden.UpdateGardeningAreaService;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.garden.DeleteGardeningAreaCommandDTOImpl;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.garden.UpdateGardeningAreaCommandDTOImpl;

@DgsComponent
public class DeleteGardeningAreaMutation {

    private final DeleteGardeningAreaService service;

    public DeleteGardeningAreaMutation(DeleteGardeningAreaService service) {
        this.service = service;
    }

    @DgsMutation
    public GardeningAreaDTO deleteGarden(@InputArgument("instructions") DeleteGardeningAreaCommandDTOImpl command, @RequestHeader("Authorization") String auth) {
        return service.delete(command, AuthMiddleware.buildAccessToken(auth));
    }
}
