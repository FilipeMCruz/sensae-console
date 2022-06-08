package pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.domain;

import pt.sensae.services.identity.management.backend.application.model.device.DeviceIdDTO;
import pt.sensae.services.identity.management.backend.application.model.domain.DomainDTO;
import pt.sensae.services.identity.management.backend.application.model.domain.DomainInfoDTO;
import pt.sensae.services.identity.management.backend.application.model.tenant.TenantDTO;

import java.util.List;

public class DomainInfoDTOImpl implements DomainInfoDTO {
    public DomainDTO domain;
    public List<DeviceIdDTO> devices;
    public List<TenantDTO> tenants;
}
