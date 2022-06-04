package pt.sensae.services.notification.management.backend.application.auth;

public interface TokenExtractor {

    TenantInfo extract(AccessTokenDTO dto);
}
