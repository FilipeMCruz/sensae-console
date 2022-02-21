package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.controller.domain;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import sharespot.services.identitymanagementbackend.application.model.domain.DomainDTO;
import sharespot.services.identitymanagementbackend.application.service.domain.CreateDomainService;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.AuthMiddleware;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.domain.CreateDomainDTOImpl;

@DgsComponent
public class DomainFactoryController {

    private final CreateDomainService service;

    public DomainFactoryController(CreateDomainService service) {
        this.service = service;
    }

    @DgsMutation(field = "createDomain")
    public DomainDTO create(@InputArgument(value = "domain") CreateDomainDTOImpl info, @RequestHeader("Authorization") String auth) {
        return service.create(info, AuthMiddleware.buildAccessToken(auth));
    }
}
