package pt.sensae.services.device.ownership.flow.domain;

import pt.sharespot.iot.core.alert.model.AlertDTO;
import pt.sharespot.iot.core.alert.routing.keys.AlertRoutingKeys;
import pt.sharespot.iot.core.keys.MessageConsumed;

import java.util.Set;

public interface UnhandledAlertRepository {

    void insert(MessageConsumed<AlertDTO, AlertRoutingKeys> data, DeviceId id);

    Set<MessageConsumed<AlertDTO, AlertRoutingKeys>> retrieve(DeviceId id);

}
