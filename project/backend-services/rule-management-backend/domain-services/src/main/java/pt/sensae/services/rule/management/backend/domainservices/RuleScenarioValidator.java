package pt.sensae.services.rule.management.backend.domainservices;

import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.springframework.stereotype.Service;
import pt.sensae.services.rule.management.backend.domain.RuleScenario;
import pt.sensae.services.rule.management.backend.domain.RuleScenarioId;
import pt.sensae.services.rule.management.backend.domain.exceptions.InvalidScenarioException;

import java.io.StringReader;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RuleScenarioValidator {

    private final KieServices kieServices;

    private final RuleScenarioCollector collector;

    public RuleScenarioValidator(KieServices kieServices, RuleScenarioCollector collector) {
        this.kieServices = kieServices;
        this.collector = collector;
    }

    public void validateDelete(RuleScenarioId id) {
        var environment = collector.collect().filter(scenario -> !scenario.id().equals(id)).collect(Collectors.toSet());
        testEnvironment(environment);
    }

    public void validateIndex(RuleScenario scenario) {
        var environment = collector.collect().collect(Collectors.toSet());
        environment.remove(scenario);
        environment.add(scenario);
        testEnvironment(environment);
    }

    private void testEnvironment(Set<RuleScenario> scenarios) {
        try {
            var kieFileSystem = kieServices.newKieFileSystem();
            scenarios.forEach(scenario -> kieFileSystem.write("src/main/resources/" + scenario.id()
                    .getValue() + ".drl", kieServices.getResources()
                    .newReaderResource(new StringReader(scenario.content().value()))));

            var kieBuilder = kieServices.newKieBuilder(kieFileSystem);
            kieBuilder.buildAll();
            if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
                var messages = kieBuilder.getResults()
                        .getMessages(Message.Level.ERROR)
                        .stream()
                        .map(Message::getText)
                        .collect(Collectors.joining("\n"));
                throw new InvalidScenarioException("Invalid Scenario Content - Messages:\n" + messages);
            }
        } catch (Exception ex) {
            throw new InvalidScenarioException("Invalid Scenario Content");
        }
    }
}
