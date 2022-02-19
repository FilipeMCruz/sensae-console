package sharespot.services.identitymanagementbackend.domain.identity.device;

import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;

public record DeviceDomainPermissions(DomainId domain, DevicePermissions permissions) {
}
