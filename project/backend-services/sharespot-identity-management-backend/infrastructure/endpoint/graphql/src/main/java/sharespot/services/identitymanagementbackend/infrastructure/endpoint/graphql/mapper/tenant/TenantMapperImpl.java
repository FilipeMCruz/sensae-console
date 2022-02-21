package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.mapper.tenant;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.mapper.tenant.TenantMapper;
import sharespot.services.identitymanagementbackend.application.model.tenant.AccessTokenDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.IdentityTokenDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.NewDomainForTenantDTO;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityCommand;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityQuery;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityResult;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.PlaceTenantInDomainCommand;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.AuthTokenHandler;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.tenant.NewDomainForTenantDTOImpl;

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
    public IdentityQuery dtoToCommand(IdentityTokenDTO dto) {
        var claims = authHandler.decode(dto);
        return mapper.convertValue(claims, IdentityQuery.class);
    }

    @Override
    public AccessTokenDTO commandToDto(IdentityResult result) {
        return authHandler.encode(result.toClaims());
    }

    @Override
    public IdentityCommand dtoToCommand(AccessTokenDTO dto) {
        var claims = authHandler.decode(dto);
        return mapper.convertValue(claims, IdentityCommand.class);
    }

    @Override
    public PlaceTenantInDomainCommand dtoToCommand(NewDomainForTenantDTO dto) {
        var info = (NewDomainForTenantDTOImpl) dto;
        var command = new PlaceTenantInDomainCommand();
        command.newDomain = UUID.fromString(info.domainOid);
        command.tenant = UUID.fromString(info.tenantOid);
        return command;
    }
}
