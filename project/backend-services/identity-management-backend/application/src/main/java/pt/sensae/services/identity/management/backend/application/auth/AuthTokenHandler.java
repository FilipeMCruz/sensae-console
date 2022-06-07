package pt.sensae.services.identity.management.backend.application.auth;

import pt.sensae.services.identity.management.backend.application.model.tenant.AccessTokenDTO;
import pt.sensae.services.identity.management.backend.application.model.tenant.IdentityTokenDTO;

import java.util.Map;

public interface AuthTokenHandler {

    Map<String, Object> decodeForMicrosoftProvider(IdentityTokenDTO token);

    Map<String, Object> decodeForGoogleProvider(IdentityTokenDTO token);

    Map<String, Object> decode(AccessTokenDTO token);

    AccessTokenDTO encode(Map<String, Object> identity);
}
