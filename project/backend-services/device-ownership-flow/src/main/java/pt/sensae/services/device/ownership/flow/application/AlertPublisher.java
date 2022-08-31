package pt.sensae.services.device.ownership.flow.application;

import pt.sharespot.iot.core.alert.model.AlertDTO;
import pt.sharespot.iot.core.alert.routing.keys.AlertRoutingKeys;
import pt.sharespot.iot.core.keys.MessageSupplied;

public interface AlertPublisher {

    void next(MessageSupplied<AlertDTO, AlertRoutingKeys> message);
}
