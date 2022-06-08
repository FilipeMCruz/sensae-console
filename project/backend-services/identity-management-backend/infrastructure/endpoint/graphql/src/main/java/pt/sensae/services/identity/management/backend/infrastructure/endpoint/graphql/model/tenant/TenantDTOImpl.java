package pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.tenant;

import pt.sensae.services.identity.management.backend.application.model.tenant.TenantDTO;

import java.util.List;

public class TenantDTOImpl implements TenantDTO {
    public String oid;
    public String email;
    
    public String name;
    
    public String phoneNumber;
    public List<String> domains;
}
