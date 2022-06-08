package pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.mapper;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.application.mapper.MainMapper;
import pt.sensae.services.identity.management.backend.application.mapper.device.DeviceMapper;
import pt.sensae.services.identity.management.backend.application.mapper.domain.DomainMapper;
import pt.sensae.services.identity.management.backend.application.mapper.tenant.TenantMapper;
import pt.sensae.services.identity.management.backend.application.model.domain.DomainInfoDTO;
import pt.sensae.services.identity.management.backend.domainservices.model.domain.DomainInfoResult;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.domain.DomainInfoDTOImpl;

@Service
public class MainMapperImpl implements MainMapper {


    private final TenantMapper tenantMapper;

    private final DomainMapper domainMapper;

    private final DeviceMapper deviceMapper;

    public MainMapperImpl(TenantMapper tenantMapper, DomainMapper domainMapper, DeviceMapper deviceMapper) {
        this.tenantMapper = tenantMapper;
        this.domainMapper = domainMapper;
        this.deviceMapper = deviceMapper;
    }

    @Override
    public DomainInfoDTO resultToDto(DomainInfoResult result) {
        var domainInfoDTO = new DomainInfoDTOImpl();
        domainInfoDTO.domain = domainMapper.resultToDto(result.domain);
        domainInfoDTO.devices = result.devices.stream().map(deviceMapper::resultToDto).toList();
        domainInfoDTO.tenants = result.tenants.stream().map(tenantMapper::resultToDto).toList();
        return domainInfoDTO;
    }
}
