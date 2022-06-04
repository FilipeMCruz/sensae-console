package pt.sensae.services.device.management.master.backend.application.ownership;

import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceId;

import java.util.Set;

public record DeviceWithAllOwnerDomains(DeviceId oid, Set<DomainId> ownerDomains) {

}
