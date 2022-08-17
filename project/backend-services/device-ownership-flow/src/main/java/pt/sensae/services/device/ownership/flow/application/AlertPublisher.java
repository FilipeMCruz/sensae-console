package pt.sensae.services.device.ownership.flow.application;

import pt.sharespot.iot.core.alert.model.AlertDTO;
import pt.sharespot.iot.core.alert.routing.keys.AlertRoutingKeys;
import pt.sharespot.iot.core.keys.MessageSupplied;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;

public interface AlertPublisher {

    void next(MessageSupplied<AlertDTO, AlertRoutingKeys> message);
}
