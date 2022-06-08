package pt.sensae.services.fleet.management.backend.application.auth;


import pt.sensae.services.fleet.management.backend.domain.auth.TenantInfo;

public interface TokenExtractor {

    TenantInfo extract(AccessTokenDTO dto);
}
