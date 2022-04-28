package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.garden;

import pt.sensae.services.smart.irrigation.backend.application.model.garden.CreateGardeningAreaCommandDTO;

import java.util.List;

public class CreateGardeningAreaCommandDTOImpl implements CreateGardeningAreaCommandDTO {

    public String name;

    public List<AreaBoundaryDTOImpl> area;

    public List<String> valves;
}
