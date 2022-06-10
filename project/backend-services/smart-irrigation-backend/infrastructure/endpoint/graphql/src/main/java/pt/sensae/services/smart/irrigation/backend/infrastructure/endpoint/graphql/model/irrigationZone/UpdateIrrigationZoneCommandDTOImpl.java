package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.irrigationZone;

import pt.sensae.services.smart.irrigation.backend.application.model.irrigationZone.UpdateIrrigationZoneCommandDTO;

import java.util.List;

public class UpdateIrrigationZoneCommandDTOImpl implements UpdateIrrigationZoneCommandDTO {

    public String id;

    public String name;

    public List<AreaBoundaryDTOImpl> area;
}
