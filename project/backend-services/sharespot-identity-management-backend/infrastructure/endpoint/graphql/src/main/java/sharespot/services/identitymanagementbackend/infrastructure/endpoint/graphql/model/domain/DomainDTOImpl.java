package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.domain;

import sharespot.services.identitymanagementbackend.application.model.domain.CreateDomainDTO;
import sharespot.services.identitymanagementbackend.application.model.domain.DomainDTO;

import java.util.List;

public class DomainDTOImpl implements DomainDTO {
    public String name;
    public String oid;
    public List<String> path;
}
