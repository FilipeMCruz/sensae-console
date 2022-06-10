package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.irrigationZone;

import pt.sensae.services.smart.irrigation.backend.application.model.irrigationZone.IrrigationZoneDTO;

import java.util.List;

public class IrrigationZoneDTOImpl implements IrrigationZoneDTO {

    public String id;

    public String name;

    public List<AreaBoundaryDTOImpl> area;
}
