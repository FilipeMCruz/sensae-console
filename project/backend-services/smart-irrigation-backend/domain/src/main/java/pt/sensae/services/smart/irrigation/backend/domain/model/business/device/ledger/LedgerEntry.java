package pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger;

import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.content.DeviceContent;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.ReportTime;

import java.util.HashSet;

public record LedgerEntry(DeviceContent content, OpenDate openAt, CloseDate closeAt,
                          Ownership ownership) {

    public boolean sameAs(LedgerEntry newLedgerEntry) {
        return this.ownership().equals(newLedgerEntry.ownership()) &&
                this.content().equals(newLedgerEntry.content());
    }

    public LedgerEntry close(CloseDate closeAt) {
        return new LedgerEntry(this.content, this.openAt, closeAt, this.ownership);
    }

    public boolean isIn(ReportTime time) {
        return time.value().isAfter(openAt.value()) &&
                (closeAt.value() == null || time.value().isBefore(closeAt.value()));
    }

    public LedgerEntryWithData toHistory() {
        return new LedgerEntryWithData(content, openAt, closeAt, new HashSet<>());
    }
}
