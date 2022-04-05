package pt.sensae.services.smart.irrigation.backend.domain.model.data.query;

import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.CloseDate;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.OpenDate;

public record DataQuery(DeviceId deviceId, OpenDate open, CloseDate close) {
}
