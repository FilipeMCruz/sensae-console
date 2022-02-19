package sharespot.services.identitymanagementbackend.domain.identity.device;

import java.util.List;

public class Device {

    private final DeviceId oid;

    private final List<DeviceDomainPermissions> domains;

    public Device(DeviceId oid, List<DeviceDomainPermissions> domains) {
        this.oid = oid;
        this.domains = domains;
    }
}
