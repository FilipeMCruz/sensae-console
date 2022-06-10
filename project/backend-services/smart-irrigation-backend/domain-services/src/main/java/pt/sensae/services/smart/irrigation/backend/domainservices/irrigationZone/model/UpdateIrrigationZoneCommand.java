package pt.sensae.services.smart.irrigation.backend.domainservices.irrigationZone.model;

import java.util.List;
import java.util.UUID;

public class UpdateIrrigationZoneCommand {

    public String name;

    public UUID id;

    public List<BoundaryCommandDetails> area;
}
