package sharespot.services.identitymanagementbackend.domainservices.model.domain;

import sharespot.services.identitymanagementbackend.domainservices.model.device.DeviceResult;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.TenantResult;

import java.util.List;

public class DomainInfoResult {
    public DomainResult domain;
    public List<TenantResult> tenants;
    public List<DeviceResult> devices;
}
