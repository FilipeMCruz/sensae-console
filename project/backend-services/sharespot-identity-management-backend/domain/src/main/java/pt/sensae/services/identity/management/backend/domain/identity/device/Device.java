package pt.sensae.services.identity.management.backend.domain.identity.device;

import pt.sensae.services.identity.management.backend.domain.identity.domain.DomainId;

import java.util.Set;

public record Device(DeviceId oid, Set<DomainId> domains) {

}
