package sharespot.services.identitymanagementslavebackend.domain.model.identity.device;

import sharespot.services.identitymanagementslavebackend.domain.model.identity.domain.DomainId;

public record DeviceDomainPermissions(DomainId domain, DevicePermissions permissions) {
}
