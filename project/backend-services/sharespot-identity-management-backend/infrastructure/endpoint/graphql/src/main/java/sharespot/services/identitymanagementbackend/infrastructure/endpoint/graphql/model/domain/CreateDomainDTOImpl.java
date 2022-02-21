package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.domain;

import sharespot.services.identitymanagementbackend.application.model.domain.CreateDomainDTO;

public class CreateDomainDTOImpl implements CreateDomainDTO {
    public String parentDomainOid;
    public String newDomainName;
}
