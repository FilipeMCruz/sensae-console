package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.graphql.auth;

import pt.sensae.services.device.management.master.backend.application.auth.AccessTokenDTO;

import java.util.Map;

public interface AuthTokenHandler {

    Map<String, Object> decode(AccessTokenDTO token);
}
