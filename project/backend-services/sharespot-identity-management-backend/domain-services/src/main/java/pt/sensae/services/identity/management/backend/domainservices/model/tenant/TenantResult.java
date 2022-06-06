package pt.sensae.services.identity.management.backend.domainservices.model.tenant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TenantResult {
    public UUID oid;
    
    public String email;

    public String name;

    public String phoneNumber;

    public List<UUID> domains;
    
    public List<UUID> parentDomains;
    
    public List<String> permissions;

    public Map<String, Object> toClaims() {
        var claims = new HashMap<String, Object>();
        claims.put("oid", oid);
        claims.put("email", email);
        claims.put("name", name);
        claims.put("domains", domains);
        claims.put("parentDomains", parentDomains);
        claims.put("permissions", permissions);
        return claims;
    }
}
