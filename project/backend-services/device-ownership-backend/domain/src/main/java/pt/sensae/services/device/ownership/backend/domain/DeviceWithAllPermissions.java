package pt.sensae.services.device.ownership.backend.domain;

import pt.sensae.services.device.ownership.backend.domain.device.DeviceId;
import pt.sensae.services.device.ownership.backend.domain.domain.DomainId;

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
