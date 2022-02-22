package sharespot.services.identitymanagementbackend.application.mapper.tenant;

import sharespot.services.identitymanagementbackend.application.model.tenant.*;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.*;

public interface TenantMapper {

    IdentityQuery dtoToCommand(IdentityTokenDTO dto);

    AccessTokenDTO commandToDto(TenantResult result);

    IdentityCommand dtoToCommand(AccessTokenDTO dto);

    PlaceTenantInDomainCommand dtoToCommand(PlaceTenantInDomainDTO dto);

    RemoveTenantFromDomainCommand dtoToCommand(ExpelTenantFromDomainDTO dto);
    
    TenantDTO resultToDto(TenantResult result);

}
