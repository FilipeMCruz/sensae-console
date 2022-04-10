package pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger;

import pt.sensae.services.smart.irrigation.backend.domain.model.data.Data;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public record DeviceLedgerWithData(Set<LedgerEntryWithData> entries) {

    public Optional<LedgerEntryWithData> getEntryIn(Data data) {
        return entries.stream().filter(entry -> entry.isIn(data.reportedAt())).findFirst();
    }

    public Optional<DeviceLedgerWithData> removeEmpty() {
        var collect = entries.stream().filter(e -> !e.dataHistory().isEmpty()).collect(Collectors.toSet());
        if (collect.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new DeviceLedgerWithData(collect));
    }
}
