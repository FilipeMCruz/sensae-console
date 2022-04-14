package pt.sensae.services.smart.irrigation.backend.domain.model.business.device.query;

import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.Ownership;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.CloseDate;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.OpenDate;

public record DeviceQuery(OpenDate open, CloseDate close, Ownership ownership) {
}
