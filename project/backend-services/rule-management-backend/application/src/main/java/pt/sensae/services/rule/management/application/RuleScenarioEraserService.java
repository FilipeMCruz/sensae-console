package pt.sensae.services.rule.management.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.rule.management.application.auth.AccessTokenDTO;
import pt.sensae.services.rule.management.application.auth.TokenExtractor;
import pt.sensae.services.rule.management.application.auth.UnauthorizedException;
import pt.sensae.services.rule.management.domainservices.RuleScenarioEraser;
import pt.sensae.services.rule.management.domainservices.RuleScenarioSelector;
import pt.sensae.services.rule.management.domainservices.RuleScenarioValidator;

@Service
public class RuleScenarioEraserService {

    private final RuleScenarioEraser eraser;

    private final RuleScenarioSelector selector;

    private final RuleScenarioValidator validator;

    private final RuleScenarioMapper mapper;

    private final RuleScenarioHandlerService publisher;

    private final TokenExtractor authHandler;

    public RuleScenarioEraserService(RuleScenarioEraser eraser,
                                     RuleScenarioSelector selector,
                                     RuleScenarioValidator validator,
                                     RuleScenarioMapper mapper,
                                     RuleScenarioHandlerService publisher,
                                     TokenExtractor authHandler) {
        this.eraser = eraser;
        this.selector = selector;
        this.validator = validator;
        this.mapper = mapper;
        this.publisher = publisher;
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

                var erased = eraser.erase(scenarioId);
                publisher.publishDelete(erased);
            }
        }
        return dto;
    }
}
