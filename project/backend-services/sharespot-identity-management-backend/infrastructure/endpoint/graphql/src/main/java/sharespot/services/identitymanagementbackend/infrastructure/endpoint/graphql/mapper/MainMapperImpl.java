package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.mapper;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.mapper.MainMapper;
import sharespot.services.identitymanagementbackend.application.mapper.device.DeviceMapper;
import sharespot.services.identitymanagementbackend.application.mapper.domain.DomainMapper;
import sharespot.services.identitymanagementbackend.application.mapper.tenant.TenantMapper;
import sharespot.services.identitymanagementbackend.application.model.domain.DomainInfoDTO;
import sharespot.services.identitymanagementbackend.domainservices.model.domain.DomainInfoResult;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.domain.DomainInfoDTOImpl;

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
