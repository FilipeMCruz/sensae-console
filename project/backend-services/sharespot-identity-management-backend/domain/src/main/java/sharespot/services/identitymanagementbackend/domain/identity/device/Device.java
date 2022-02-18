package sharespot.services.identitymanagementbackend.domain.identity.device;

import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;

import java.util.List;

public class Device {

    private final DeviceId oid;

    private final List<DomainId> domains;

    public Device(DeviceId oid, List<DomainId> domains) {
        this.oid = oid;
        this.domains = domains;
    }
}
