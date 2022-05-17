package sharespot.services.identitymanagementslavebackend.domain.model.identity;

import sharespot.services.identitymanagementslavebackend.domain.model.identity.device.DeviceId;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.domain.DomainId;

import java.util.List;
import java.util.Set;

public class DeviceWithAllPermissions {

    private final DeviceId oid;

    private final Set<DomainId> ownerDomains;

    public DeviceWithAllPermissions(DeviceId oid, Set<DomainId> ownerDomains) {
        this.oid = oid;
        this.ownerDomains = ownerDomains;
    }

    public DeviceId getOid() {
        return oid;
    }

    public Set<DomainId> getOwnerDomains() {
        return ownerDomains;
    }
}
