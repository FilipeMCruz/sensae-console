package pt.sensae.services.identity.management.backend.domainservices.model.domain;

import java.util.UUID;

public class CreateDomainCommand {
    public UUID parentDomainId;
    public UUID domainId;
    public String domainName;
}
