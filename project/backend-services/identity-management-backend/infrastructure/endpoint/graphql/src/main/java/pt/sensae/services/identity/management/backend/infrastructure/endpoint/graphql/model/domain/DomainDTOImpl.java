package pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.domain;

import pt.sensae.services.identity.management.backend.application.model.domain.DomainDTO;

import java.util.List;

public class DomainDTOImpl implements DomainDTO {
    public String name;
    public String oid;
    public List<String> path;
    public List<String> permissions;
}
