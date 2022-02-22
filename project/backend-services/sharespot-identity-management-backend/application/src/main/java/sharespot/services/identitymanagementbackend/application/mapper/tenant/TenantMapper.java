package sharespot.services.identitymanagementbackend.application.mapper.tenant;

import sharespot.services.identitymanagementbackend.application.model.tenant.AccessTokenDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.IdentityTokenDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.NewDomainForTenantDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.TenantDTO;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityCommand;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityQuery;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.PlaceTenantInDomainCommand;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.TenantResult;

public interface TenantMapper {

    IdentityQuery dtoToCommand(IdentityTokenDTO dto);

    AccessTokenDTO commandToDto(TenantResult result);

    IdentityCommand dtoToCommand(AccessTokenDTO dto);

    PlaceTenantInDomainCommand dtoToCommand(NewDomainForTenantDTO dto);

    TenantDTO resultToDto(TenantResult result);

}
