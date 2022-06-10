package pt.sensae.services.smart.irrigation.backend.domainservices.device.model;

import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.DeviceId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.Ownership;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone.IrrigationZoneId;

import java.util.Set;

public record LatestDataQuery(Set<IrrigationZoneId> irrigationZones, Set<DeviceId> deviceIds, Ownership value) {

}
