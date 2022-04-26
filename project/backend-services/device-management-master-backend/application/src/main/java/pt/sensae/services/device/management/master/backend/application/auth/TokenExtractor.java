package pt.sensae.services.device.management.master.backend.application.auth;

import pt.sensae.services.device.management.master.backend.domainservices.auth.TenantInfo;

public interface TokenExtractor {

    TenantInfo extract(AccessTokenDTO dto);
}
