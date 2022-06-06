package pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.tenant;

import pt.sensae.services.identity.management.backend.application.model.tenant.UpdateTenantDTO;

public class UpdateTenantDTOImpl implements UpdateTenantDTO {
    public String name;
    public String phoneNumber;
}
