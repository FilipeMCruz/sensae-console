package pt.sensae.services.data.processor.master.backend.infrastructure.endpoint.graphql.auth;

import pt.sensae.services.data.processor.master.backend.application.auth.AccessTokenDTO;

import java.util.Map;

public interface AuthTokenHandler {

    Map<String, Object> decode(AccessTokenDTO token);
}
