package pt.sensae.services.alert.dispatcher.backend.application;

import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;
import pt.sensae.services.alert.dispatcher.backend.domain.RuleScenario;
import pt.sensae.services.alert.dispatcher.backend.domain.RuleScenarioNotificationType;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.stream.Stream;

@Service
public class RulesContainer {

    private final KieServices kieServices;

    private final KieFileSystem kieFileSystem;

    private final AlertHandlerService publisherService;

    private KieSession session;

    private Thread engine;

    public RulesContainer(KieServices kieServices, AlertHandlerService publisherService) {
        this.kieServices = kieServices;
        this.kieFileSystem = kieServices.newKieFileSystem();
        this.publisherService = publisherService;
        init();
    }

    public KieSession getSession() {
        return this.session;
    }

    public void updateEnvironment(Stream<RuleScenario> rules) {
        rules.forEach(rule -> {
            kieFileSystem.delete("src/main/resources/" + rule.id().getValue() + ".drl");
            if (rule.notificationType().equals(RuleScenarioNotificationType.UPDATE)) {
                kieFileSystem.write("src/main/resources/" + rule.id().getValue() + ".drl", kieServices.getResources()
                        .newReaderResource(new StringReader(rule.content().value())));
            }
        });
        reload();
    }

    private void reload() {
        try {
            var kieBuilder = kieServices.newKieBuilder(kieFileSystem);
            kieBuilder.buildAll();

            if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
                return;
            }
            var kieContainer = kieServices.newKieContainer(kieBuilder.getKieModule().getReleaseId());
            if (this.session != null) {
                this.session.dispose();
            }
            if (this.engine != null) {
                this.engine.join();
            }

            var config = kieServices.newKieBaseConfiguration();
            config.setOption(EventProcessingOption.STREAM);
            var kieBase = kieContainer.newKieBase(config);
            this.session = kieBase.newKieSession();
            this.session.setGlobal("dispatcher", publisherService);
            this.engine = new Thread(new RuleEngineRunnable(this.session));
            this.engine.start();
        } catch (Exception ignore) {
        }
    }

    private void init() {
        try {
            Arrays.stream(new PathMatchingResourcePatternResolver().getResources("classpath*:rules/**/*.*"))
                    .map(file -> ResourceFactory.newClassPathResource("rules/" + file.getFilename(), "UTF-8"))
                    .forEach(kieFileSystem::write);
            reload();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
