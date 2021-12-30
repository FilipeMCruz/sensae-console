package sharespot.services.simpleauth.infrastructure.endpoint.graphql.model;

import sharespot.services.simpleauth.application.UserCredentialsDTO;

public class UserCredentialsDTOImpl implements UserCredentialsDTO {

    public String name;

    public String secret;

    public UserCredentialsDTOImpl() {
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSecret() {
        return secret;
    }
}
