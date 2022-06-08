package pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.controller.domain;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.identity.management.backend.application.model.domain.DomainDTO;
import pt.sensae.services.identity.management.backend.application.service.domain.ChangeDomainService;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.AuthMiddleware;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.domain.ChangeDomainDTOImpl;

@DgsComponent
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
