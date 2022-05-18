package sharespot.services.data.decoder.master.application.auth;

import sharespot.services.datadecodermaster.domainservices.auth.TenantInfo;

public interface TokenExtractor {

    TenantInfo extract(AccessTokenDTO dto);
}
