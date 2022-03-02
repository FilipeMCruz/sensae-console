package sharespot.services.dataprocessormaster.infrastructure.endpoint.graphql.auth;

import sharespot.services.dataprocessormaster.application.auth.AccessTokenDTO;

import java.util.Map;

public interface AuthTokenHandler {

    Map<String, Object> decode(AccessTokenDTO token);
}
