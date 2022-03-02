package sharespot.services.devicerecordsbackend.application.auth;

import sharespot.services.devicerecordsbackend.domainservices.auth.TenantInfo;

public interface TokenExtractor {

    TenantInfo extract(AccessTokenDTO dto);
}
