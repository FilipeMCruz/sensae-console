package sharespot.services.identitymanagementbackend.infrastructure.endpoint.amqp.mapper;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.internal.tenant.TenantIdentityMapper;
import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.Tenant;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.amqp.model.TenantIdentityDTOImpl;

import java.util.stream.Collectors;

@Service
public class TenantIdentityMapperImpl implements TenantIdentityMapper {

    public TenantIdentityDTOImpl domainToDto(Tenant tenant) {
        var dto = new TenantIdentityDTOImpl();
        dto.id = tenant.oid().value();
        dto.email = tenant.email().value();
        dto.phoneNumber = tenant.phoneNumber().value();
        dto.domains = tenant.domains().stream().map(DomainId::value).collect(Collectors.toSet());
        dto.name = tenant.name().value();
        return dto;
    }
}
