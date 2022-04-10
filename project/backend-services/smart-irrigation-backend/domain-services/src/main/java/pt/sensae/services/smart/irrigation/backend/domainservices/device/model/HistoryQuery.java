package pt.sensae.services.smart.irrigation.backend.domainservices.device.model;

import pt.sensae.services.smart.irrigation.backend.domain.model.DomainId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.CloseDate;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.OpenDate;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.Ownership;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.query.DeviceQuery;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningAreaId;

import java.util.Set;

public record HistoryQuery(Set<GardeningAreaId> gardens,
                           Set<DeviceId> deviceIds,
                           Set<DomainId> domains,
                           OpenDate openDate,
                           CloseDate closeDate) {
    public DeviceQuery toDeviceQuery() {
        return new DeviceQuery(openDate, closeDate, Ownership.of(domains.stream()));
    }
}
