package sharespot.services.devicerecordsbackend.infrastructure.endpoint.graphql.auth;

import sharespot.services.devicerecordsbackend.application.auth.AccessTokenDTO;

import java.util.Map;

public interface AuthTokenHandler {

    Map<String, Object> decode(AccessTokenDTO token);
}
