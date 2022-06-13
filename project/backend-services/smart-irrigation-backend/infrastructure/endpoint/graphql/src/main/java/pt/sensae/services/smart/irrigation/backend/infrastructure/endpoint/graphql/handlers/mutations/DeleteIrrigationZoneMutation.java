package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.handlers.mutations;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.smart.irrigation.backend.application.model.irrigationZone.IrrigationZoneDTO;
import pt.sensae.services.smart.irrigation.backend.application.services.irrigationZone.DeleteIrrigationZoneService;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.irrigationZone.DeleteIrrigationZoneCommandDTOImpl;

@DgsComponent
public class DeleteIrrigationZoneMutation {

    private final DeleteIrrigationZoneService service;

    public DeleteIrrigationZoneMutation(DeleteIrrigationZoneService service) {
        this.service = service;
    }

    @DgsMutation
    public IrrigationZoneDTO deleteIrrigationZone(@InputArgument("instructions") DeleteIrrigationZoneCommandDTOImpl command, @RequestHeader("Authorization") String auth) {
        return service.delete(command, AuthMiddleware.buildAccessToken(auth));
    }
}
