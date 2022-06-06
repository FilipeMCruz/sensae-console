package pt.sensae.services.identity.management.backend.domainservices.model.device;

import java.util.UUID;

public class RemoveDeviceFromDomainCommand {
    public UUID domain;
    public UUID device;
}
