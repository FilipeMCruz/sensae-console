package sharespot.services.identitymanagementbackend.domainservices.model.tenant;

import java.util.UUID;

public class RemoveTenantFromDomainCommand {
    public UUID domain;
    public UUID tenant;
}
