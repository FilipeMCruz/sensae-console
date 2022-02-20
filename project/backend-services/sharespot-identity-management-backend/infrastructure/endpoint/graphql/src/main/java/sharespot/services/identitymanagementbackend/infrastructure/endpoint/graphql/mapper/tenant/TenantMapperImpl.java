package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.mapper.tenant;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.mapper.tenant.TenantMapper;
import sharespot.services.identitymanagementbackend.application.model.tenant.AuthenticationDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.JWTTokenDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.NewDomainForTenantDTO;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityCommand;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityQuery;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityResult;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.PlaceTenantInDomainCommand;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.AuthTokenHandler;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.tenant.AuthenticationDTOImpl;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.tenant.NewDomainForTenantDTOImpl;

import java.util.Arrays;
import java.util.UUID;

@Service
public class TenantMapperImpl implements TenantMapper {

    private final AuthTokenHandler authHandler;

    public TenantMapperImpl(AuthTokenHandler authHandler) {
        this.authHandler = authHandler;
    }

    @Override
    public IdentityQuery dtoToCommand(AuthenticationDTO dto) {
        var authInfo = (AuthenticationDTOImpl) dto;
        var identityQuery = new IdentityQuery();
        identityQuery.email = authInfo.email;
        identityQuery.name = authInfo.name;
        identityQuery.oid = authInfo.oid;
        return identityQuery;
    }

    @Override
    public JWTTokenDTO commandToDto(IdentityResult result) {
        return authHandler.encode(result.toClaims());
    }

    @Override
    public IdentityCommand dtoToCommand(JWTTokenDTO dto) {
        var claims = authHandler.decode(dto);

        var identityCommand = new IdentityCommand();
        identityCommand.oid = claims.get("oid", UUID.class);
        identityCommand.email = claims.get("email", String.class);
        identityCommand.name = claims.get("name", String.class);
        identityCommand.domains = Arrays.stream(claims.get("domains", String[].class)).toList();
        return identityCommand;
    }

    @Override
    public PlaceTenantInDomainCommand dtoToCommand(NewDomainForTenantDTO dto) {
        var info = (NewDomainForTenantDTOImpl) dto;
        var command = new PlaceTenantInDomainCommand();
        command.newDomain = info.domainOid;
        command.tenant = info.tenantOid;
        return command;
    }
}
