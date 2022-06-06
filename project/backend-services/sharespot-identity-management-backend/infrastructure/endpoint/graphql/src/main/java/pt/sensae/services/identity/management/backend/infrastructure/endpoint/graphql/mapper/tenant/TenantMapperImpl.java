package pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.mapper.tenant;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.application.auth.AuthTokenHandler;
import pt.sensae.services.identity.management.backend.application.mapper.tenant.TenantMapper;
import pt.sensae.services.identity.management.backend.application.model.tenant.*;
import pt.sensae.services.identity.management.backend.domainservices.model.tenant.*;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.tenant.ExpelTenantFromDomainDTOImpl;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.tenant.PlaceTenantInDomainDTOImpl;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.tenant.TenantDTOImpl;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.tenant.UpdateTenantDTOImpl;

import java.util.UUID;

@Service
public class TenantMapperImpl implements TenantMapper {

    private final AuthTokenHandler authHandler;

    private final ObjectMapper mapper;

    public TenantMapperImpl(AuthTokenHandler authHandler, ObjectMapper mapper) {
        this.authHandler = authHandler;
        this.mapper = mapper;
    }

    @Override
    public IdentityQuery dtoToCommand(IdentityTokenDTO dto, boolean isMicrosoft) {
        if (isMicrosoft) {
            var claims = authHandler.decodeForMicrosoftProvider(dto);
            return mapper.convertValue(claims, MicrosoftIdentityQuery.class);
        } else {
            var claims = authHandler.decodeForGoogleProvider(dto);
            return mapper.convertValue(claims, GoogleIdentityQuery.class);
        }
    }

    @Override
    public AccessTokenDTO commandToDto(TenantResult result) {
        return authHandler.encode(result.toClaims());
    }

    @Override
    public IdentityCommand dtoToCommand(AccessTokenDTO dto) {
        var claims = authHandler.decode(dto);
        return mapper.convertValue(claims, IdentityCommand.class);
    }

    @Override
    public PlaceTenantInDomainCommand dtoToCommand(PlaceTenantInDomainDTO dto) {
        var info = (PlaceTenantInDomainDTOImpl) dto;
        var command = new PlaceTenantInDomainCommand();
        command.newDomain = UUID.fromString(info.domainOid);
        command.tenant = UUID.fromString(info.tenantOid);
        return command;
    }

    @Override
    public RemoveTenantFromDomainCommand dtoToCommand(ExpelTenantFromDomainDTO dto) {
        var info = (ExpelTenantFromDomainDTOImpl) dto;
        var command = new RemoveTenantFromDomainCommand();
        command.domain = UUID.fromString(info.domainOid);
        command.tenant = UUID.fromString(info.tenantOid);
        return command;
    }

    @Override
    public UpdateTenantProfileCommand dtoToCommand(UpdateTenantDTO dto) {
        var info = (UpdateTenantDTOImpl) dto;
        var command = new UpdateTenantProfileCommand();
        command.name = info.name;
        command.phoneNumber = info.phoneNumber;
        return command;
    }

    @Override
    public TenantDTO resultToDto(TenantResult result) {
        var dto = new TenantDTOImpl();
        dto.oid = result.oid.toString();
        dto.email = result.email;
        dto.name = result.name;
        dto.phoneNumber = result.phoneNumber;
        dto.domains = result.domains.stream().map(UUID::toString).toList();
        return dto;
    }
}
