package pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.mapper;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.application.internal.identity.tenant.TenantIdentityMapper;
import pt.sensae.services.identity.management.backend.domain.identity.domain.DomainId;
import pt.sensae.services.identity.management.backend.domain.identity.tenant.Tenant;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.amqp.model.identity.tenant.TenantIdentityDTOImpl;

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
