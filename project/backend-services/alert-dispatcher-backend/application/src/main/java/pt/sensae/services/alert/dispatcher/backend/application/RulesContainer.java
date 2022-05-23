package pt.sensae.services.alert.dispatcher.backend.application;

import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

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

//    public boolean removeRule(Rule rule) {
//        kieFileSystem.delete("src/main/resources/" + rule.name + ".drl");
//        return reload();
//    }
//
//    public boolean updateRule(Rule rule) {
//        kieFileSystem.delete("src/main/resources/" + rule.name + ".drl");
//        kieFileSystem.write("src/main/resources/" + rule.name + ".drl", kieServices.getResources()
//                .newReaderResource(new StringReader(rule.ruleContent)));
//        return reload();
//    }

    private boolean reload() {
        try {
            var kieBuilder = kieServices.newKieBuilder(kieFileSystem);
            kieBuilder.buildAll();

            if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
                return false;
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
            return true;
        } catch (Exception ex) {
            return false;
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

    public KieSession getSession() {
        return this.session;
    }
}
