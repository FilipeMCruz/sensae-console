package pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql;

import pt.sensae.services.identity.management.backend.application.auth.AccessTokenDTOImpl;
import pt.sensae.services.identity.management.backend.application.auth.IdentityTokenDTOImpl;

public class AuthMiddleware {

    public static AccessTokenDTOImpl buildAccessToken(String authHeader) {
        if (authHeader == null || authHeader.length() < 10 || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid Access token");
        }
        var accessTokenDTO = new AccessTokenDTOImpl();
        accessTokenDTO.token = authHeader.substring(7);
        return accessTokenDTO;
    }

    public static IdentityTokenDTOImpl buildIdentityToken(String authHeader) {
        if (authHeader == null || authHeader.length() < 10 || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid Access token");
        }
        var accessTokenDTO = new IdentityTokenDTOImpl();
        accessTokenDTO.token = authHeader.substring(7);
        return accessTokenDTO;
    }
}
