package pt.sensae.services.identity.management.backend.domainservices.model.tenant;

import java.util.UUID;

public class RemoveTenantFromDomainCommand {
    public UUID domain;
    public UUID tenant;
}
