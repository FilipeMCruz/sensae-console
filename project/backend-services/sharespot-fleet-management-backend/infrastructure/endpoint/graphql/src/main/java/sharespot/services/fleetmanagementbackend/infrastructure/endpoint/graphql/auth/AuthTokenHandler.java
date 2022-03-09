package sharespot.services.fleetmanagementbackend.infrastructure.endpoint.graphql.auth;

import sharespot.services.fleetmanagementbackend.application.auth.AccessTokenDTO;

import java.util.Map;

public interface AuthTokenHandler {

    Map<String, Object> decode(AccessTokenDTO token);
}
