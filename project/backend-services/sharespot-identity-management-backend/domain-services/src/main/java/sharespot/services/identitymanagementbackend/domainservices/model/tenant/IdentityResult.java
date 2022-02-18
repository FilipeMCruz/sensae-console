package sharespot.services.identitymanagementbackend.domainservices.model.tenant;

import java.util.List;
import java.util.UUID;

public class IdentityResult {
    public UUID oid;
    public String email;
    public String name;
    public List<UUID> domains;
}
