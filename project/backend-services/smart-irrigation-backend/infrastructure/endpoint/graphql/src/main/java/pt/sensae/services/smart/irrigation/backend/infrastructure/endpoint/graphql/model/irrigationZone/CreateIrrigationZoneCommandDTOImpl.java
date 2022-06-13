package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.irrigationZone;

import pt.sensae.services.smart.irrigation.backend.application.model.irrigationZone.CreateIrrigationZoneCommandDTO;

import java.util.List;

public class CreateIrrigationZoneCommandDTOImpl implements CreateIrrigationZoneCommandDTO {

    public String name;

    public List<AreaBoundaryDTOImpl> area;
}
