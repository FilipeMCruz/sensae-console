package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.handlers.mutations;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.smart.irrigation.backend.application.services.irrigationZone.UpdateIrrigationZoneService;
import pt.sensae.services.smart.irrigation.backend.application.model.irrigationZone.IrrigationZoneDTO;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.irrigationZone.UpdateIrrigationZoneCommandDTOImpl;

@DgsComponent
public class UpdateIrrigationZoneMutation {

    private final UpdateIrrigationZoneService service;

    public UpdateIrrigationZoneMutation(UpdateIrrigationZoneService service) {
        this.service = service;
    }

    @DgsMutation
    public IrrigationZoneDTO updateIrrigationZone(@InputArgument("instructions") UpdateIrrigationZoneCommandDTOImpl command, @RequestHeader("Authorization") String auth) {
        return service.update(command, AuthMiddleware.buildAccessToken(auth));
    }
}
