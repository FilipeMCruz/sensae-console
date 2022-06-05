package sharespot.services.identitymanagementbackend.domainservices.model.tenant;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MicrosoftIdentityQuery implements IdentityQuery {

    @JsonProperty("preferred_username")
    public String preferredUsername;

    public String name;

    @Override
    public String getEmail() {
        return preferredUsername;
    }

    @Override
    public String getName() {
        return name;
    }
}
