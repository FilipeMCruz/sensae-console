package pt.sensae.services.rule.management.backend.infrastructure.endpoint.graphql.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.rule.management.backend.application.RuleScenarioDTO;
import pt.sensae.services.rule.management.backend.application.RuleScenarioRegisterService;
import pt.sensae.services.rule.management.backend.infrastructure.endpoint.graphql.model.RuleScenarioDTOImpl;
import pt.sensae.services.rule.management.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;

@DgsComponent
public class RuleScenarioRegisterController {

    private final RuleScenarioRegisterService service;

    public RuleScenarioRegisterController(RuleScenarioRegisterService service) {
        this.service = service;
    }

    @DgsMutation(field = "index")
    public RuleScenarioDTO index(@InputArgument(value = "scenario") RuleScenarioDTOImpl dto, @RequestHeader("Authorization") String auth) {
        return service.register(dto, AuthMiddleware.buildAccessToken(auth));
    }
}
