package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.tenant;

import sharespot.services.identitymanagementbackend.application.model.tenant.ExpelTenantFromDomainDTO;

public class ExpelTenantFromDomainDTOImpl implements ExpelTenantFromDomainDTO {
    public String tenantOid;
    public String domainOid;
}
