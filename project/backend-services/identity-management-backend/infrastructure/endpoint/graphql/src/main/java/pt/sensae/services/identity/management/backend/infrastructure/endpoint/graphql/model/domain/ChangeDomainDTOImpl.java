package pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.domain;

import pt.sensae.services.identity.management.backend.application.model.domain.ChangeDomainDTO;

import java.util.List;

public class ChangeDomainDTOImpl implements ChangeDomainDTO {
    public String domainId;
    public String domainName;
    public List<String> permissions;
}
