package pt.sensae.services.smart.irrigation.backend.domain.model.ledger;

import pt.sensae.services.smart.irrigation.backend.domain.model.Ownership;
import pt.sensae.services.smart.irrigation.backend.domain.model.content.DeviceContent;

public record LedgerEntry(DeviceContent content, OpenDate open, CloseDate close, Ownership ownership) {
}
