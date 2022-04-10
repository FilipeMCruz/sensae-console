package pt.sensae.services.smart.irrigation.backend.domain.model.data.query;

import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.CloseDate;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.OpenDate;

import java.util.Set;

public record DataQuery(Set<DeviceId> deviceId, OpenDate open, CloseDate close) {
}
