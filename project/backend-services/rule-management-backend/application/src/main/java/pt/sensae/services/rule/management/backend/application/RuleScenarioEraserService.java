package pt.sensae.services.rule.management.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.rule.management.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.rule.management.backend.application.auth.TokenExtractor;
import pt.sensae.services.rule.management.backend.application.auth.UnauthorizedException;
import pt.sensae.services.rule.management.backend.domainservices.RuleScenarioEraser;
import pt.sensae.services.rule.management.backend.domainservices.RuleScenarioSelector;
import pt.sensae.services.rule.management.backend.domainservices.RuleScenarioValidator;

@Service
public class RuleScenarioEraserService {

    private final RuleScenarioEraser eraser;

    private final RuleScenarioSelector selector;

    private final RuleScenarioValidator validator;

    private final RuleScenarioMapper mapper;

    private final TokenExtractor authHandler;

    public RuleScenarioEraserService(RuleScenarioEraser eraser,
                                     RuleScenarioSelector selector,
                                     RuleScenarioValidator validator,
                                     RuleScenarioMapper mapper,
                                     TokenExtractor authHandler) {
        this.eraser = eraser;
        this.selector = selector;
        this.validator = validator;
        this.mapper = mapper;
        this.authHandler = authHandler;
    }

    public RuleScenarioIdDTO erase(RuleScenarioIdDTO dto, AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("rule_management:rules:delete"))
            throw new UnauthorizedException("No Permissions");

        var scenarioId = mapper.dtoToDomain(dto);

        var scenarioOpt = selector.findById(scenarioId);
        if (scenarioOpt.isPresent()) {
            var old = scenarioOpt.get();
            if (old.owners().domains().stream().noneMatch(domain -> extract.domains.contains(domain.toString()))) {
                throw new UnauthorizedException("No Permissions");
            } else {
                validator.validateDelete(scenarioId);
                eraser.erase(scenarioId);
            }
        }
        return dto;
    }
}
