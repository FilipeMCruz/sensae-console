package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.domain;

import sharespot.services.identitymanagementbackend.application.model.device.DeviceIdDTO;
import sharespot.services.identitymanagementbackend.application.model.domain.DomainDTO;
import sharespot.services.identitymanagementbackend.application.model.domain.DomainInfoDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.TenantDTO;

import java.util.List;

public class DomainInfoDTOImpl implements DomainInfoDTO {
    public DomainDTO domain;
    public List<DeviceIdDTO> devices;
    public List<TenantDTO> tenants;
}
