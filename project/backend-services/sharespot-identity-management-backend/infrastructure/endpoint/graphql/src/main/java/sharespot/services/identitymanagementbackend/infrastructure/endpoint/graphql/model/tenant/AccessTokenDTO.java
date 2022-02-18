package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.tenant;

import sharespot.services.identitymanagementbackend.application.model.tenant.JWTTokenDTO;

public class AccessTokenDTO implements JWTTokenDTO {

    public String token;
}
