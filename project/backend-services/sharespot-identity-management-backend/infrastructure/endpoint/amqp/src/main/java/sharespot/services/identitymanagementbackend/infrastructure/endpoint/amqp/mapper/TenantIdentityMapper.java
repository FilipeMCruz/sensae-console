package sharespot.services.identitymanagementbackend.infrastructure.endpoint.amqp.mapper;

import sharespot.services.identitymanagementbackend.domain.identity.domain.DomainId;
import sharespot.services.identitymanagementbackend.domain.identity.tenant.Tenant;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.amqp.model.TenantIdentityDTOImpl;

import java.util.stream.Collectors;

public class TenantIdentityMapper {

    public static TenantIdentityDTOImpl domainToDto(Tenant tenant) {
        var dto = new TenantIdentityDTOImpl();
        dto.id = tenant.oid().value();
        dto.email = tenant.email().value();
        dto.phoneNumber = ""; //TODO
        dto.domains = tenant.domains().stream().map(DomainId::value).collect(Collectors.toSet());
        dto.name = tenant.name().value();
        return dto;
    }
}
