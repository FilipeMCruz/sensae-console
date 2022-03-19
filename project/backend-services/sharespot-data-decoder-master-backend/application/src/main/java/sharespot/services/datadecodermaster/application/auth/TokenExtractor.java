package sharespot.services.datadecodermaster.application.auth;

import sharespot.services.datadecodermaster.domainservices.auth.TenantInfo;

public interface TokenExtractor {

    TenantInfo extract(AccessTokenDTO dto);
}
