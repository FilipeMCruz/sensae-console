package sharespot.services.identitymanagementbackend.domain.identity.device;

import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;

import java.util.Set;

public record Device(DeviceId oid, Set<DomainId> domains) {

}
