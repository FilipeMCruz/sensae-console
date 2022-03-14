package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.domain;

import sharespot.services.identitymanagementbackend.application.model.domain.ChangeDomainDTO;

import java.util.List;

public class ChangeDomainDTOImpl implements ChangeDomainDTO {
    public String domainId;
    public String domainName;
    public List<String> permissions;
}
