package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.mapper.irrigationZone;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.application.mapper.irrigationZone.IrrigationZoneCommandDTOMapper;
import pt.sensae.services.smart.irrigation.backend.application.model.irrigationZone.CreateIrrigationZoneCommandDTO;
import pt.sensae.services.smart.irrigation.backend.application.model.irrigationZone.DeleteIrrigationZoneCommandDTO;
import pt.sensae.services.smart.irrigation.backend.application.model.irrigationZone.UpdateIrrigationZoneCommandDTO;
import pt.sensae.services.smart.irrigation.backend.domainservices.irrigationZone.model.BoundaryCommandDetails;
import pt.sensae.services.smart.irrigation.backend.domainservices.irrigationZone.model.CreateIrrigationZoneCommand;
import pt.sensae.services.smart.irrigation.backend.domainservices.irrigationZone.model.DeleteIrrigationZoneCommand;
import pt.sensae.services.smart.irrigation.backend.domainservices.irrigationZone.model.UpdateIrrigationZoneCommand;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.irrigationZone.AreaBoundaryDTOImpl;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.irrigationZone.CreateIrrigationZoneCommandDTOImpl;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.irrigationZone.DeleteIrrigationZoneCommandDTOImpl;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.irrigationZone.UpdateIrrigationZoneCommandDTOImpl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class IrrigationZoneCommandDTOMapperImpl implements IrrigationZoneCommandDTOMapper {

    @Override
    public UpdateIrrigationZoneCommand toCommand(UpdateIrrigationZoneCommandDTO dto) {
        var dtoCom = (UpdateIrrigationZoneCommandDTOImpl) dto;
        var com = new UpdateIrrigationZoneCommand();
        com.id = UUID.fromString(dtoCom.id);
        com.name = dtoCom.name;
        com.area = extracted(dtoCom.area);
        return com;
    }

    @Override
    public CreateIrrigationZoneCommand toCommand(CreateIrrigationZoneCommandDTO dto) {
        var dtoCom = (CreateIrrigationZoneCommandDTOImpl) dto;
        var com = new CreateIrrigationZoneCommand();
        com.name = dtoCom.name;
        com.area = extracted(dtoCom.area);
        return com;
    }

    @Override
    public DeleteIrrigationZoneCommand toCommand(DeleteIrrigationZoneCommandDTO dto) {
        var dtoCom = (DeleteIrrigationZoneCommandDTOImpl) dto;
        var com = new DeleteIrrigationZoneCommand();
        com.id = UUID.fromString(dtoCom.id);
        return com;
    }

    private List<BoundaryCommandDetails> extracted(List<AreaBoundaryDTOImpl> dtoCom) {
        return dtoCom.stream().map(b -> {
            var out = new BoundaryCommandDetails();
            out.position = b.position;
            out.altitude = Float.valueOf(b.altitude);
            out.latitude = Double.valueOf(b.latitude);
            out.longitude = Double.valueOf(b.longitude);
            return out;
        }).collect(Collectors.toList());
    }
}
