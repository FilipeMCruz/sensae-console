package pt.sensae.services.smart.irrigation.backend.domainservices.device;

import pt.sensae.services.smart.irrigation.backend.domain.model.DomainId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.CloseDate;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.OpenDate;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.Ownership;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.query.DeviceQuery;

import java.util.stream.Stream;

public record HistoryQuery(Stream<DeviceId> deviceIds, Stream<DomainId> domains, OpenDate openDate,
                           CloseDate closeDate) {
    public DeviceQuery toDeviceQuery() {
        return new DeviceQuery(openDate, closeDate, Ownership.of(domains));
    }

}
