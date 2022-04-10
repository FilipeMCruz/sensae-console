package pt.sensae.services.smart.irrigation.backend.domainservices.device.model;

import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.Ownership;

import java.util.Set;

public record LatestDataQuery(Set<DeviceId> deviceIds, Ownership value) {

}
