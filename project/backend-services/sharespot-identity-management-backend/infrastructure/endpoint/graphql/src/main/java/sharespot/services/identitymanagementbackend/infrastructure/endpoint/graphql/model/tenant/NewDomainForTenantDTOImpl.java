package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.tenant;

import sharespot.services.identitymanagementbackend.application.model.tenant.NewDomainForTenantDTO;

import java.util.UUID;

public class NewDomainForTenantDTOImpl implements NewDomainForTenantDTO {
    public UUID tenantOid;
    public UUID domainOid;
}
