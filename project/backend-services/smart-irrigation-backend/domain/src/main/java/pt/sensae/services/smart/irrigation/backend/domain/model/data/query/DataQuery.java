package pt.sensae.services.smart.irrigation.backend.domain.model.data.query;

import pt.sensae.services.smart.irrigation.backend.domain.model.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.ledger.CloseDate;
import pt.sensae.services.smart.irrigation.backend.domain.model.ledger.OpenDate;

public record DataQuery(DeviceId deviceId, OpenDate open, CloseDate close) {
}
