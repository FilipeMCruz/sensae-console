package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.tenant;

import sharespot.services.identitymanagementbackend.application.model.tenant.TenantDTO;

import java.util.List;

public class TenantDTOImpl implements TenantDTO {
    public String oid;
    public String email;
    public String name;
    public List<String> domains;
}
