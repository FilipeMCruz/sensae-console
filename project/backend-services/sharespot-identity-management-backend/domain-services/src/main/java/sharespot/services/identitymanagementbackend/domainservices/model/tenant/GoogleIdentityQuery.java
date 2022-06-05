package sharespot.services.identitymanagementbackend.domainservices.model.tenant;

public class GoogleIdentityQuery implements IdentityQuery {

    public String email;

    public String name;

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return name;
    }
}
