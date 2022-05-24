package pt.sensae.services.rule.management.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.rule.management.application.auth.AccessTokenDTO;
import pt.sensae.services.rule.management.application.auth.TokenExtractor;
import pt.sensae.services.rule.management.application.auth.UnauthorizedException;
import pt.sensae.services.rule.management.domainservices.RuleScenarioHoarder;
import pt.sensae.services.rule.management.domainservices.RuleScenarioSelector;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RuleScenarioRegisterService {

    private final RuleScenarioHoarder hoarder;

    private final RuleScenarioSelector selector;

    private final RuleScenarioMapper mapper;

    private final RuleScenarioHandlerService publisher;

    private final TokenExtractor authHandler;

    public RuleScenarioRegisterService(RuleScenarioHoarder hoarder,
                                       RuleScenarioSelector selector, RuleScenarioMapper mapper,
                                       RuleScenarioHandlerService publisher,
                                       TokenExtractor authHandler) {
        this.hoarder = hoarder;
        this.selector = selector;
        this.mapper = mapper;
        this.publisher = publisher;
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
        var hoard = hoarder.hoard(ruleScenario);
        publisher.publishUpdate(hoard);

        return dto;
    }
}
