package pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger;

import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.content.DeviceContent;

public record LedgerEntry(DeviceContent content, OpenDate openAt, CloseDate closeAt, Ownership ownership) {

    public LedgerEntry {
        openAt.isBefore(closeAt);
    }

    public boolean sameAs(LedgerEntry newLedgerEntry) {
        return this.ownership().equals(newLedgerEntry.ownership()) &&
                this.content().equals(newLedgerEntry.content());
    }

    public LedgerEntry close(CloseDate closeAt) {
        return new LedgerEntry(this.content, this.openAt, closeAt, this.ownership);
    }
}
