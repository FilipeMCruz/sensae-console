package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.garden;

import pt.sensae.services.smart.irrigation.backend.application.model.garden.UpdateGardeningAreaCommandDTO;

import java.util.List;

public class UpdateGardeningAreaCommandDTOImpl implements UpdateGardeningAreaCommandDTO {

    public String id;

    public String name;

    public List<AreaBoundaryDTO> area;

    public List<String> valves;

    public GardeningAreaTypeDTO type;
}
