package pt.sensae.services.rule.management.infrastructure.endpoint.graphql.auth;

import pt.sensae.services.rule.management.application.auth.AccessTokenDTO;

import java.util.Map;

public interface AuthTokenHandler {

    Map<String, Object> decode(AccessTokenDTO token);
}
