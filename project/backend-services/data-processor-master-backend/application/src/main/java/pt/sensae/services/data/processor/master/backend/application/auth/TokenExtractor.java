package pt.sensae.services.data.processor.master.backend.application.auth;

import pt.sensae.services.data.processor.master.backend.domainservices.auth.TenantInfo;

public interface TokenExtractor {

    TenantInfo extract(AccessTokenDTO dto);
}
