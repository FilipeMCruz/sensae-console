package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.handlers.mutations;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.smart.irrigation.backend.application.services.irrigationZone.CreateIrrigationZoneService;
import pt.sensae.services.smart.irrigation.backend.application.model.irrigationZone.IrrigationZoneDTO;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.irrigationZone.CreateIrrigationZoneCommandDTOImpl;

@DgsComponent
public class CreateIrrigationZoneMutation {

    private final CreateIrrigationZoneService service;

    public CreateIrrigationZoneMutation(CreateIrrigationZoneService service) {
        this.service = service;
    }

    @DgsMutation
    public IrrigationZoneDTO createIrrigationZone(@InputArgument("instructions") CreateIrrigationZoneCommandDTOImpl command, @RequestHeader("Authorization") String auth) {
        return service.create(command, AuthMiddleware.buildAccessToken(auth));
    }
}
