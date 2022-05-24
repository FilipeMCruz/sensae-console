package pt.sensae.services.rule.management.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.rule.management.application.auth.AccessTokenDTO;
import pt.sensae.services.rule.management.application.auth.TokenExtractor;
import pt.sensae.services.rule.management.application.auth.UnauthorizedException;
import pt.sensae.services.rule.management.domain.RuleScenarioOwners;
import pt.sensae.services.rule.management.domainservices.RuleScenarioCollector;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RuleScenarioCollectorService {

    private final RuleScenarioCollector collector;

    private final RuleScenarioMapper mapper;

    private final TokenExtractor authHandler;

    public RuleScenarioCollectorService(RuleScenarioCollector collector,
                                        RuleScenarioMapper mapper,
                                        TokenExtractor authHandler) {
        this.collector = collector;
        this.mapper = mapper;
        this.authHandler = authHandler;
    }

    public Stream<RuleScenarioDTO> transformations(AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("rule_management:rules:read"))
            throw new UnauthorizedException("No Permissions");

        var tenantDomains = extract.domains.stream()
                .map(UUID::fromString)
                .collect(Collectors.toSet());
        
        return collector.collect(RuleScenarioOwners.of(tenantDomains))
                .map(mapper::domainToDto);
    }
}
