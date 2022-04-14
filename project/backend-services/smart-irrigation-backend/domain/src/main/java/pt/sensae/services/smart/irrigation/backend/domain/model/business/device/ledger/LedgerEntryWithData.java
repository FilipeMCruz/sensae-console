package pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger;

import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.content.DeviceContent;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.Data;
import pt.sensae.services.smart.irrigation.backend.domain.model.data.ReportTime;

import java.util.Set;

public record LedgerEntryWithData(DeviceContent content, OpenDate openAt, CloseDate closeAt, Set<Data> dataHistory) {
    public void addData(Data data) {
        dataHistory.add(data);
    }

    public boolean isIn(ReportTime time) {
        return time.value().isAfter(openAt.value()) &&
                (closeAt.value() == null || time.value().isBefore(closeAt.value()));
    }
}
