package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.tenant;

import sharespot.services.identitymanagementbackend.application.model.tenant.AuthenticationDTO;

import java.util.UUID;

public class AuthenticationDTOImpl implements AuthenticationDTO {
    public UUID oid;
    public String email;
    public String name;
}
