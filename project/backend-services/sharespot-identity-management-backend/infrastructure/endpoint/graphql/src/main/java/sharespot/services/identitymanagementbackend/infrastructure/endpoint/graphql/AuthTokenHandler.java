package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql;

import sharespot.services.identitymanagementbackend.application.model.tenant.AccessTokenDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.IdentityTokenDTO;

import java.util.Map;

public interface AuthTokenHandler {

    Map<String, Object> decode(IdentityTokenDTO token);

    Map<String, Object> decode(AccessTokenDTO token);

    AccessTokenDTO encode(Map<String, Object> identity);
}
