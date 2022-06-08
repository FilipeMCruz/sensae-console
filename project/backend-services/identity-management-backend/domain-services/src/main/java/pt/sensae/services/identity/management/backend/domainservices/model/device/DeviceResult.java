package pt.sensae.services.identity.management.backend.domainservices.model.device;

import java.util.List;

public class DeviceResult {
    public String oid;
    
    public String name;
    
    public List<DeviceDomainPermissionsResult> domains;
}