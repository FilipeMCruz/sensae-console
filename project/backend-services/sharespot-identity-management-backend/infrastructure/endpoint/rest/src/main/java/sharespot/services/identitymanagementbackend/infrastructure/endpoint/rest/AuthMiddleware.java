package sharespot.services.identitymanagementbackend.infrastructure.endpoint.rest;

import sharespot.services.identitymanagementbackend.application.auth.AccessTokenDTOImpl;

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
