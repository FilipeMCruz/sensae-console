package sharespot.services.datadecodermaster.infrastructure.endpoint.graphql.auth;

import sharespot.services.datadecodermaster.application.auth.AccessTokenDTO;

import java.util.Map;

public interface AuthTokenHandler {

    Map<String, Object> decode(AccessTokenDTO token);
}
