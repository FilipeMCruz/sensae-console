package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.graphql.auth;

import pt.sensae.services.device.management.master.backend.application.auth.AccessTokenDTOImpl;

public class AuthMiddleware {

    public static AccessTokenDTOImpl buildAccessToken(String authHeader) {
        if (authHeader == null || authHeader.length() < 10 || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid Access token");
        }
        var accessTokenDTO = new AccessTokenDTOImpl();
        accessTokenDTO.token = authHeader.substring(7);
        return accessTokenDTO;
    }
}
