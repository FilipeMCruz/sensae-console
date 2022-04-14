package pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger;

import java.util.Set;
import java.util.stream.Collectors;

public record DeviceLedger(Set<LedgerEntry> entries) {
    public static DeviceLedger start(LedgerEntry ledgerEntry) {
        return new DeviceLedger(Set.of(ledgerEntry));
    }

    public DeviceLedgerWithData toHistory() {
        return new DeviceLedgerWithData(entries.stream().map(LedgerEntry::toHistory).collect(Collectors.toSet()));
    }
}
