package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.tenant;

import sharespot.services.identitymanagementbackend.application.model.tenant.UpdateTenantDTO;

public class UpdateTenantDTOImpl implements UpdateTenantDTO {
    public String name;
    public String phoneNumber;
}
