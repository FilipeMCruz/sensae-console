package pt.sensae.services.device.ownership.backend.domain.device;

import pt.sensae.services.device.ownership.backend.domain.domain.DomainId;

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
