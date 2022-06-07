package pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.domain;

import pt.sensae.services.identity.management.backend.application.model.domain.CreateDomainDTO;

public class CreateDomainDTOImpl implements CreateDomainDTO {
    public String parentDomainOid;
    public String newDomainName;
}
