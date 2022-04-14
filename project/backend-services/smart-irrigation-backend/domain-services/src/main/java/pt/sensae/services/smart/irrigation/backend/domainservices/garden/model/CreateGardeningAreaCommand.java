package pt.sensae.services.smart.irrigation.backend.domainservices.garden.model;

import java.util.List;

public class CreateGardeningAreaCommand {

    public String name;

    public String type;

    public List<BoundaryCommandDetails> area;
}
