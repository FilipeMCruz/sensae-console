package pt.sensae.services.smart.irrigation.backend.domain.model.ledger;

import java.util.Set;

public record DeviceLedger(Set<LedgerEntry> entries) {
}
