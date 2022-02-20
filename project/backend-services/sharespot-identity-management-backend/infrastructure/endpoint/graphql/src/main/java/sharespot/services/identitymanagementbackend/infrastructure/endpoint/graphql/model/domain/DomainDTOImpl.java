package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.domain;

import sharespot.services.identitymanagementbackend.application.model.domain.DomainDTO;

import java.util.UUID;

public class DomainDTOImpl implements DomainDTO {
    public UUID parentDomainId;
    public UUID newDomainId;
    public String newDomainName;
}
