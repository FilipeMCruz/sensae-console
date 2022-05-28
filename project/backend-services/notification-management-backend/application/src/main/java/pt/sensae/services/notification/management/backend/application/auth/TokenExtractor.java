package pt.sensae.services.notification.management.backend.application.auth;

import pt.sensae.services.notification.management.backend.domainservices.auth.TenantInfo;

public interface TokenExtractor {

    TenantInfo extract(AccessTokenDTO dto);
}
