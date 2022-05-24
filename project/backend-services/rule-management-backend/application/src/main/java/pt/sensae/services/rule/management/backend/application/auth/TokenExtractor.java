package pt.sensae.services.rule.management.backend.application.auth;

import pt.sensae.services.rule.management.backend.domainservices.auth.TenantInfo;

public interface TokenExtractor {

    TenantInfo extract(AccessTokenDTO dto);
}
