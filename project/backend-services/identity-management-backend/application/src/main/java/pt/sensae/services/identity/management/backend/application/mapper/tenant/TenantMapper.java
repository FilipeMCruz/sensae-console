package pt.sensae.services.identity.management.backend.application.mapper.tenant;

import pt.sensae.services.identity.management.backend.application.model.tenant.*;
import pt.sensae.services.identity.management.backend.domainservices.model.tenant.*;

public interface TenantMapper {

    IdentityQuery dtoToCommand(IdentityTokenDTO dto, boolean isMicrosoft);

    AccessTokenDTO commandToDto(TenantResult result);

    IdentityCommand dtoToCommand(AccessTokenDTO dto);

    PlaceTenantInDomainCommand dtoToCommand(PlaceTenantInDomainDTO dto);

    RemoveTenantFromDomainCommand dtoToCommand(ExpelTenantFromDomainDTO dto);
    UpdateTenantProfileCommand dtoToCommand(UpdateTenantDTO dto);
    
    TenantDTO resultToDto(TenantResult result);

}
