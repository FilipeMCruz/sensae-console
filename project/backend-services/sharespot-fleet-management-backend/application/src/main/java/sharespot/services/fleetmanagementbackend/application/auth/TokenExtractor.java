package sharespot.services.fleetmanagementbackend.application.auth;


import sharespot.services.fleetmanagementbackend.domain.auth.TenantInfo;

public interface TokenExtractor {

    TenantInfo extract(AccessTokenDTO dto);
}
