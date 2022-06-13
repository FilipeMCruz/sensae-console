package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data;

import pt.sensae.services.smart.irrigation.backend.application.model.data.LiveDataFilterDTO;

import java.util.Set;

public class LiveDataFilterDTOImpl implements LiveDataFilterDTO {
    public Set<String> irrigationZones;
    public Set<String> devices;
    public String content;
}
