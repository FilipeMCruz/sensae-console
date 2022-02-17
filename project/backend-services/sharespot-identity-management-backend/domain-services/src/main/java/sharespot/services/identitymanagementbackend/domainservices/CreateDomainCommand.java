package sharespot.services.identitymanagementbackend.domainservices;

import java.util.UUID;

public class CreateDomainCommand {
    public UUID parentDomainId;
    public UUID domainId;
    public String domainName;
}
