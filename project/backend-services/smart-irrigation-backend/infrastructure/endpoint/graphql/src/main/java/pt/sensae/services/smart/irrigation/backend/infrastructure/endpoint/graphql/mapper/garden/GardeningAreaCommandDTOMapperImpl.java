package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.mapper.garden;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.application.mapper.garden.GardeningAreaCommandDTOMapper;
import pt.sensae.services.smart.irrigation.backend.application.model.garden.CreateGardeningAreaCommandDTO;
import pt.sensae.services.smart.irrigation.backend.application.model.garden.UpdateGardeningAreaCommandDTO;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.model.BoundaryCommandDetails;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.model.CreateGardeningAreaCommand;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.model.UpdateGardeningAreaCommand;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.garden.AreaBoundaryDTO;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.garden.CreateGardeningAreaCommandDTOImpl;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.garden.UpdateGardeningAreaCommandDTOImpl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GardeningAreaCommandDTOMapperImpl implements GardeningAreaCommandDTOMapper {

    @Override
    public UpdateGardeningAreaCommand toCommand(UpdateGardeningAreaCommandDTO dto) {
        var dtoCom = (UpdateGardeningAreaCommandDTOImpl) dto;
        var com = new UpdateGardeningAreaCommand();
        com.id = UUID.fromString(dtoCom.id);
        com.name = dtoCom.name;
        com.area = extracted(dtoCom.area);
        return com;
    }

    @Override
    public CreateGardeningAreaCommand toCommand(CreateGardeningAreaCommandDTO dto) {
        var dtoCom = (CreateGardeningAreaCommandDTOImpl) dto;
        var com = new CreateGardeningAreaCommand();
        com.name = dtoCom.name;
        com.area = extracted(dtoCom.area);
        return com;
    }

    private List<BoundaryCommandDetails> extracted(List<AreaBoundaryDTO> dtoCom) {
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
