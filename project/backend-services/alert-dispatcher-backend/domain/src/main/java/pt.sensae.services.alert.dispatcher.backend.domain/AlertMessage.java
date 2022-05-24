package pt.sensae.services.alert.dispatcher.backend.domain;

import pt.sharespot.iot.core.alert.model.AlertDTO;
import pt.sharespot.iot.core.alert.routing.keys.AlertRoutingKeys;

public record AlertMessage(AlertDTO alert, AlertRoutingKeys routingKeys) {

}