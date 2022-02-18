package sharespot.services.identitymanagementbackend.application.service.tenant;

import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.mapper.tenant.TenantMapper;
import sharespot.services.identitymanagementbackend.application.model.tenant.AuthenticationDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.JWTTokenDTO;
import sharespot.services.identitymanagementbackend.domainservices.service.tenant.AuthenticateTenant;

@Service
public class AuthenticateTenantService {
    private final AuthenticateTenant service;

    private final TenantMapper mapper;

    public AuthenticateTenantService(AuthenticateTenant service, TenantMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    public JWTTokenDTO authenticate(AuthenticationDTO dto) {
        var identity = mapper.dtoToCommand(dto);
        var result = service.execute(identity);
        return mapper.commandToDto(result);
    }
}
