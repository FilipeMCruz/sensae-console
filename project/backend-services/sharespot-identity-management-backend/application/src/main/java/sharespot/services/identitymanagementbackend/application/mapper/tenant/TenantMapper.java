package sharespot.services.identitymanagementbackend.application.mapper.tenant;

import sharespot.services.identitymanagementbackend.application.model.tenant.AccessTokenDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.IdentityTokenDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.NewDomainForTenantDTO;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityCommand;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityQuery;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityResult;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.PlaceTenantInDomainCommand;

public interface TenantMapper {

    IdentityQuery dtoToCommand(IdentityTokenDTO dto);

    AccessTokenDTO commandToDto(IdentityResult result);

    IdentityCommand dtoToCommand(AccessTokenDTO dto);

    PlaceTenantInDomainCommand dtoToCommand(NewDomainForTenantDTO dto);
}
