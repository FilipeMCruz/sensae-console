package pt.sensae.services.identity.management.backend.domainservices.model.domain;

import pt.sensae.services.identity.management.backend.domainservices.model.tenant.TenantResult;
import pt.sensae.services.identity.management.backend.domainservices.model.device.DeviceResult;

import java.util.List;

public class DomainInfoResult {
    public DomainResult domain;
    public List<TenantResult> tenants;
    public List<DeviceResult> devices;
}
