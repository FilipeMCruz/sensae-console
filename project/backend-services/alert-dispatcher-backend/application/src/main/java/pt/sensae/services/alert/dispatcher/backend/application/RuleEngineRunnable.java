package pt.sensae.services.alert.dispatcher.backend.application;

import org.kie.api.runtime.KieSession;

public class RuleEngineRunnable implements Runnable {

    private final KieSession session;

    public RuleEngineRunnable(KieSession session) {
        this.session = session;
    }

    @Override
    public void run() {
        session.fireUntilHalt();
    }
}
