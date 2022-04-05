package pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger;

import java.util.Set;

public record DeviceLedger(Set<LedgerEntry> entries) {
    public static DeviceLedger start(LedgerEntry ledgerEntry) {
        return new DeviceLedger(Set.of(ledgerEntry));
    }
}
