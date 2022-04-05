package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.auth;


import pt.sensae.services.smart.irrigation.backend.application.auth.AccessTokenDTO;

import java.util.Map;

public interface AuthTokenHandler {

    Map<String, Object> decode(AccessTokenDTO token);
}
