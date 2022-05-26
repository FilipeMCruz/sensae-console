package pt.sensae.services.rule.management.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.rule.management.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.rule.management.backend.application.auth.TokenExtractor;
import pt.sensae.services.rule.management.backend.application.auth.UnauthorizedException;
import pt.sensae.services.rule.management.backend.domainservices.RuleScenarioHoarder;
import pt.sensae.services.rule.management.backend.domainservices.RuleScenarioSelector;
import pt.sensae.services.rule.management.backend.domainservices.RuleScenarioValidator;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RuleScenarioRegisterService {

    private final RuleScenarioHoarder hoarder;

    private final RuleScenarioValidator validator;

    private final RuleScenarioSelector selector;

    private final RuleScenarioMapper mapper;

    private final TokenExtractor authHandler;

    public RuleScenarioRegisterService(RuleScenarioHoarder hoarder,
                                       RuleScenarioValidator validator,
                                       RuleScenarioSelector selector,
                                       RuleScenarioMapper mapper,
                                       TokenExtractor authHandler) {
        this.hoarder = hoarder;
        this.validator = validator;
        this.selector = selector;
        this.mapper = mapper;
        this.authHandler = authHandler;
    }

    public RuleScenarioDTO register(RuleScenarioDTO dto, AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("rule_management:rules:edit"))
            throw new UnauthorizedException("No Permissions");

        var tenantDomains = Stream.concat(extract.domains.stream(), extract.parentDomains.stream())
                .map(UUID::fromString)
                .collect(Collectors.toSet());

        var ruleScenario = mapper.dtoToDomain(dto, tenantDomains);

        var scenarioOpt = selector.findById(ruleScenario.id());
        if (scenarioOpt.isPresent()) {
            var old = scenarioOpt.get();
            if (old.owners().domains().stream().noneMatch(domain -> extract.domains.contains(domain.toString()))) {
                throw new UnauthorizedException("No Permissions");
            }
            ruleScenario = old.withContent(ruleScenario.content());
        }

        validator.validateIndex(ruleScenario);

        var hoard = hoarder.hoard(ruleScenario);
        return mapper.domainToDto(hoard);
    }
}
