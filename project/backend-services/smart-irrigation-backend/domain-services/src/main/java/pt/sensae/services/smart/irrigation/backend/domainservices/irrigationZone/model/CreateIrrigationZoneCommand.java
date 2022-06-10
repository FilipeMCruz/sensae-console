package pt.sensae.services.smart.irrigation.backend.domainservices.irrigationZone.model;

import java.util.List;

public class CreateIrrigationZoneCommand {

    public String name;

    public List<BoundaryCommandDetails> area;
}
