package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql;

import io.jsonwebtoken.Claims;
import sharespot.services.identitymanagementbackend.application.model.tenant.JWTTokenDTO;

import java.util.Map;

public interface AuthTokenHandler {

    Claims decode(JWTTokenDTO token);

    JWTTokenDTO encode(Map<String, Object> identity);
}
