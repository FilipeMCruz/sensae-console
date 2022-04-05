package pt.sensae.services.smart.irrigation.backend.application.auth;

import pt.sensae.services.smart.irrigation.backend.domain.auth.TenantInfo;

public interface TokenExtractor {

    TenantInfo extract(AccessTokenDTO dto);
}
