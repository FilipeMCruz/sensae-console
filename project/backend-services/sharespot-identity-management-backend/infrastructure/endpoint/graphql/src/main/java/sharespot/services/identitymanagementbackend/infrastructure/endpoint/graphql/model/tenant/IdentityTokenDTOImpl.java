package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.tenant;

import sharespot.services.identitymanagementbackend.application.model.tenant.AccessTokenDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.IdentityTokenDTO;

public class IdentityTokenDTOImpl implements IdentityTokenDTO {

    public String token;
}
