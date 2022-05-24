package pt.sensae.services.rule.management.application.auth;

import pt.sensae.services.rule.management.domainservices.auth.TenantInfo;

public interface TokenExtractor {

    TenantInfo extract(AccessTokenDTO dto);
}
