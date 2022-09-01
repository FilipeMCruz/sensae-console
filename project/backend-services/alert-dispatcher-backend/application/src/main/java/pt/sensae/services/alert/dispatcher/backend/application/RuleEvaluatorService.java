package pt.sensae.services.alert.dispatcher.backend.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.data.model.DataUnitDTO;

@Service
public class RuleEvaluatorService {

    private final RulesContainer rulesContainer;

    public RuleEvaluatorService(RulesContainer rulesContainer) {
        this.rulesContainer = rulesContainer;
    }

    public void insertData(DataUnitDTO data) {
        rulesContainer.getSession().insert(data);
    }
}

