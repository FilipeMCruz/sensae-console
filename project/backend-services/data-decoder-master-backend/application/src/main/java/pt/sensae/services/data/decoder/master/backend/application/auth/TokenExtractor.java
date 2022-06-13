package pt.sensae.services.data.decoder.master.backend.application.auth;

import pt.sensae.services.data.decoder.master.backend.domainservices.auth.TenantInfo;

public interface TokenExtractor {

    TenantInfo extract(AccessTokenDTO dto);
}
