package sharespot.services.identitymanagementbackend.domainservices.model.tenant;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdentityQuery {

    @JsonProperty("preferred_username")
    public String preferredUsername;

    public String name;
}
