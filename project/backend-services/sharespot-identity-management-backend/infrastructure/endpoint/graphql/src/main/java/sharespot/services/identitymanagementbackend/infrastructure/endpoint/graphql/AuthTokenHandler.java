package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql;

import io.jsonwebtoken.Claims;
import sharespot.services.identitymanagementbackend.application.model.tenant.AccessTokenDTO;

import java.util.Map;

public interface AuthTokenHandler {

    Map<String, Object> decode(AccessTokenDTO token);

    AccessTokenDTO encode(Map<String, Object> identity);
}
