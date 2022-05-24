package pt.sensae.services.rule.management.backend.infrastructure.endpoint.graphql.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.rule.management.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;
import pt.sensae.services.rule.management.backend.application.RuleScenarioCollectorService;
import pt.sensae.services.rule.management.backend.application.RuleScenarioDTO;

import java.util.Set;
import java.util.stream.Collectors;

@DgsComponent
public class RuleScenarioCollectorController {

    private final RuleScenarioCollectorService service;

    public RuleScenarioCollectorController(RuleScenarioCollectorService service) {
        this.service = service;
    }

    @DgsQuery(field = "scenario")
    public Set<RuleScenarioDTO> collect(@RequestHeader("Authorization") String auth) {
        return service.ruleScenarios(AuthMiddleware.buildAccessToken(auth))
                .collect(Collectors.toSet());
    }
}
