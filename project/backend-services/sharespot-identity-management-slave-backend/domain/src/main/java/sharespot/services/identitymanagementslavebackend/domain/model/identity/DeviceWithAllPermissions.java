package sharespot.services.identitymanagementslavebackend.domain.model.identity;

import sharespot.services.identitymanagementslavebackend.domain.model.identity.device.DeviceId;
import sharespot.services.identitymanagementslavebackend.domain.model.identity.domain.DomainId;

import java.util.List;

public class DeviceWithAllPermissions {

    private final DeviceId oid;

    private final List<DomainId> readDomains;

    private final List<DomainId> readWriteDomains;

    public DeviceWithAllPermissions(DeviceId oid, List<DomainId> readDomains, List<DomainId> readWriteDomains) {
        this.oid = oid;
        this.readDomains = readDomains;
        this.readWriteDomains = readWriteDomains;
    }

    public DeviceId getOid() {
        return oid;
    }

    public List<DomainId> getReadDomains() {
        return readDomains;
    }

    public List<DomainId> getReadWriteDomains() {
        return readWriteDomains;
    }
}
