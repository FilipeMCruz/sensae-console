package pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger;

import pt.sensae.services.smart.irrigation.backend.domain.model.data.Data;

import java.util.Optional;
import java.util.Set;

public record DeviceLedgerWithData(Set<LedgerEntryWithData> entries) {

    public boolean isIn(Data data) {
        return entries.stream().anyMatch(entry -> entry.isIn(data.reportedAt()));
    }

    public Optional<LedgerEntryWithData> getEntryIn(Data data) {
        return entries.stream().filter(entry -> entry.isIn(data.reportedAt())).findFirst();
    }
}
