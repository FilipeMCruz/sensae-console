package pt.sensae.services.rule.management.infrastructure.endpoint.graphql.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.rule.management.infrastructure.endpoint.graphql.auth.AuthMiddleware;
import pt.sensae.services.rule.management.infrastructure.endpoint.graphql.model.RuleScenarioIdDTOImpl;
import pt.sensae.services.rule.management.application.RuleScenarioIdDTO;
import pt.sensae.services.rule.management.application.RuleScenarioEraserService;

@DgsComponent
public class RuleScenarioEraserController {

    private final RuleScenarioEraserService service;

    public RuleScenarioEraserController(RuleScenarioEraserService service) {
        this.service = service;
    }

    @DgsMutation(field = "delete")
    public RuleScenarioIdDTO delete(@InputArgument(value = "id") RuleScenarioIdDTOImpl dto, @RequestHeader("Authorization") String auth) {
        return service.erase(dto, AuthMiddleware.buildAccessToken(auth));
    }
}
