package pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.tenant;

import pt.sensae.services.identity.management.backend.application.model.tenant.ExpelTenantFromDomainDTO;

public class ExpelTenantFromDomainDTOImpl implements ExpelTenantFromDomainDTO {
    public String tenantOid;
    public String domainOid;
}
