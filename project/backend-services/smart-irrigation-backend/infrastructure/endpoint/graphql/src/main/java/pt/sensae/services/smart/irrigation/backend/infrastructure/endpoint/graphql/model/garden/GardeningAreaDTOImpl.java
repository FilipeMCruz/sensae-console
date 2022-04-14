package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.garden;

import pt.sensae.services.smart.irrigation.backend.application.model.garden.GardeningAreaDTO;

import java.util.List;

public class GardeningAreaDTOImpl implements GardeningAreaDTO {

    public String id;

    public String name;

    public List<AreaBoundaryDTO> area;
}
