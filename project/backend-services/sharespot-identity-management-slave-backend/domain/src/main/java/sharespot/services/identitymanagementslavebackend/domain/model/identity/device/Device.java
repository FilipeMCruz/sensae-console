package sharespot.services.identitymanagementslavebackend.domain.model.identity.device;

import sharespot.services.identitymanagementslavebackend.domain.model.identity.domain.DomainId;

import java.util.List;

public class Device {

    private final DeviceId oid;

    private final List<DomainId> domains;

    public Device(DeviceId oid, List<DomainId> domains) {
        this.oid = oid;
        this.domains = domains;
    }

    public DeviceId getOid() {
        return oid;
    }

    public List<DomainId> getDomains() {
        return domains;
    }
}
