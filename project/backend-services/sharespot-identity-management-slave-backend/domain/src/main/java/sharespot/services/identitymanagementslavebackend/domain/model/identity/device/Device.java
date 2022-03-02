package sharespot.services.identitymanagementslavebackend.domain.model.identity.device;

import java.util.List;

public class Device {

    private final DeviceId oid;

    private final List<DeviceDomainPermissions> domains;

    public Device(DeviceId oid, List<DeviceDomainPermissions> domains) {
        this.oid = oid;
        this.domains = domains;
    }

    public DeviceId getOid() {
        return oid;
    }

    public List<DeviceDomainPermissions> getDomains() {
        return domains;
    }
}