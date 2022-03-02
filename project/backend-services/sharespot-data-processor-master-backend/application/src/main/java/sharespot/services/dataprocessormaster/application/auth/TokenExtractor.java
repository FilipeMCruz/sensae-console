package sharespot.services.dataprocessormaster.application.auth;

import sharespot.services.dataprocessormaster.domainservices.auth.TenantInfo;

public interface TokenExtractor {

    TenantInfo extract(AccessTokenDTO dto);
}
