package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql;

import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.tenant.AccessTokenDTOImpl;

public class AuthMiddleware {

    public static AccessTokenDTOImpl build(String authHeader) {
        if (authHeader == null || authHeader.length() < 10 || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid Access token");
        }
        var accessTokenDTO = new AccessTokenDTOImpl();
        accessTokenDTO.token = authHeader.substring(7);
        return accessTokenDTO;
    }
}
