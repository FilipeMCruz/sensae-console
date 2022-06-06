package pt.sensae.services.identity.management.backend.application.service.tenant;

import org.springframework.stereotype.Service;
import pt.sensae.services.identity.management.backend.application.model.tenant.AccessTokenDTO;
import pt.sensae.services.identity.management.backend.application.model.tenant.IdentityTokenDTO;
import pt.sensae.services.identity.management.backend.application.model.tenant.TenantDTO;
import pt.sensae.services.identity.management.backend.application.mapper.tenant.TenantMapper;
import pt.sensae.services.identity.management.backend.domainservices.service.tenant.AuthenticateTenant;

@Service
public class AuthenticateTenantService {

    private final AuthenticateTenant service;

    private final TenantMapper mapper;

    public AuthenticateTenantService(AuthenticateTenant service, TenantMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    public AccessTokenDTO authenticate(String provider, IdentityTokenDTO dto) {
        var identity = mapper.dtoToCommand(dto, provider.equalsIgnoreCase("microsoft"));
        var result = service.execute(identity);
        return mapper.commandToDto(result);
    }

    public AccessTokenDTO authenticate(AccessTokenDTO dto) {
        var identity = mapper.dtoToCommand(dto);
        var result = service.execute(identity);
        return mapper.commandToDto(result);
    }

    public TenantDTO fetch(AccessTokenDTO dto) {
        var identity = mapper.dtoToCommand(dto);
        var result = service.execute(identity);
        return mapper.resultToDto(result);
    }
}
