package pt.sensae.services.rule.management.backend.application;

import pt.sharespot.iot.core.alert.model.AlertDTO;
import pt.sharespot.iot.core.alert.model.AlertDispatcherService;

public class AlertHandlerServiceMock implements AlertDispatcherService {
    @Override
    public void publish(AlertDTO alert) {

    }
}
