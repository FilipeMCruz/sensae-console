package pt.sensae.services.device.ownership.flow.domain;

import java.util.Set;

public record DeviceWithAllPermissions(DeviceId oid, Set<DomainId> ownerDomains) {

}
