package sharespot.services.identitymanagementbackend.domain.identity.device;

import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;

import java.util.Set;

public record DeviceWithAllOwnerDomains(DeviceId oid, Set<DomainId> ownerDomains) {

}
