package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data;

import pt.sensae.services.smart.irrigation.backend.application.model.LiveDataFilterDTO;

import java.util.Set;

public class LiveDataFilterDTOImpl implements LiveDataFilterDTO {
    public Set<String> gardens;
    public Set<String> devices;
    public String content;
}