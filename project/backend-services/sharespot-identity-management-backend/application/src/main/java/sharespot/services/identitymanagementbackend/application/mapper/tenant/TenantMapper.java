package sharespot.services.identitymanagementbackend.application.mapper.tenant;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.model.tenant.AuthenticationDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.JWTTokenDTO;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityCommand;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityQuery;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityResult;

@Service
public interface TenantMapper {

    IdentityQuery dtoToCommand(AuthenticationDTO dto);

    JWTTokenDTO commandToDto(IdentityResult result);

    IdentityCommand dtoToCommand(JWTTokenDTO dto);
}
