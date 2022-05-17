package sharespot.services.identitymanagementslavebackend.domain.model.identity;

import sharespot.services.identitymanagementslavebackend.domain.model.identity.device.DeviceId;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.domain.DomainId;

import java.util.List;

public class DeviceWithAllPermissions {

    private final DeviceId oid;

    private final List<DomainId> ownerDomains;

    public DeviceWithAllPermissions(DeviceId oid, List<DomainId> ownerDomains) {
        this.oid = oid;
        this.ownerDomains = ownerDomains;
    }

    public DeviceId getOid() {
        return oid;
    }

    public List<DomainId> getOwnerDomains() {
        return ownerDomains;
    }
}
