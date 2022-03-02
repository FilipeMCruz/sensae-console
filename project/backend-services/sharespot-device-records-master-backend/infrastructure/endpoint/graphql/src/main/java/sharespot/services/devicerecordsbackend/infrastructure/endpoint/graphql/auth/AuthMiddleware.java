package sharespot.services.devicerecordsbackend.infrastructure.endpoint.graphql.auth;

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
