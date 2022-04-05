package pt.sensae.services.smart.irrigation.backend.domain.model.device.query;

import pt.sensae.services.smart.irrigation.backend.domain.model.Ownership;
import pt.sensae.services.smart.irrigation.backend.domain.model.ledger.CloseDate;
import pt.sensae.services.smart.irrigation.backend.domain.model.ledger.OpenDate;

public record DeviceQuery(OpenDate open, CloseDate close, Ownership ownership) {
}
