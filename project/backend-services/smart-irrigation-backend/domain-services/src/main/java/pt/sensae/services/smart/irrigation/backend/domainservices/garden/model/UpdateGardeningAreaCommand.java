package pt.sensae.services.smart.irrigation.backend.domainservices.garden.model;

import java.util.List;
import java.util.UUID;

public class UpdateGardeningAreaCommand {

    public String name;

    public UUID id;

    public List<BoundaryCommandDetails> area;
}
