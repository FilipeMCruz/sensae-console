package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.controller.domain;

import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import sharespot.services.identitymanagementbackend.application.model.domain.DomainDTO;
import sharespot.services.identitymanagementbackend.application.service.domain.ChangeDomainService;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.AuthMiddleware;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.domain.ChangeDomainDTOImpl;

@Controller
public class DomainModifierController {

    private final ChangeDomainService service;

    public DomainModifierController(ChangeDomainService service) {
        this.service = service;
    }

    @DgsMutation(field = "changeDomain")
    public DomainDTO change(@InputArgument(value = "domain") ChangeDomainDTOImpl info, @RequestHeader("Authorization") String auth) {
        return service.change(info, AuthMiddleware.buildAccessToken(auth));
    }
}
