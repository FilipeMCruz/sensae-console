package sharespot.services.identitymanagementbackend.domainservices.model.domain;

import java.util.UUID;

public class ViewDomainQuery {
    public UUID topDomainId;

    public static ViewDomainQuery of(UUID domain) {
        var viewDomainQuery = new ViewDomainQuery();
        viewDomainQuery.topDomainId = domain;
        return viewDomainQuery;
    }
}
