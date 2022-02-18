package sharespot.services.identitymanagementbackend.domainservices.model.tenant;

import java.util.List;
import java.util.UUID;

public class IdentityCommand {
    public String email;
    public String name;
    public UUID oid;
    public List<String> domains;
}
