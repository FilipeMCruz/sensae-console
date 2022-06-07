package pt.sensae.services.identity.management.backend.domainservices.model.domain;

import java.util.List;
import java.util.UUID;

public class ChangeDomainCommand {
    public UUID domainId;
    public String domainName;
    public List<String> permissions;
}
