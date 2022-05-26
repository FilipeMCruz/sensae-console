package sharespot.services.data.decoder.master.infrastructure.endpoint.graphql.auth;

import sharespot.services.data.decoder.master.application.auth.AccessTokenDTO;

import java.util.Map;

public interface AuthTokenHandler {

    Map<String, Object> decode(AccessTokenDTO token);
}
