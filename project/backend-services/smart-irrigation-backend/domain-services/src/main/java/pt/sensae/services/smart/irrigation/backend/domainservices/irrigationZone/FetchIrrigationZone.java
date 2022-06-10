package pt.sensae.services.smart.irrigation.backend.domainservices.irrigationZone;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.domain.auth.TenantInfo;
import pt.sensae.services.smart.irrigation.backend.domain.model.DomainId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.Ownership;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone.IrrigationZone;

import java.util.UUID;
import java.util.stream.Stream;

@Service
public class FetchIrrigationZone {

    private final IrrigationZoneCache cache;

    public FetchIrrigationZone(IrrigationZoneCache cache) {
        this.cache = cache;
    }

    public Stream<IrrigationZone> fetchAll(TenantInfo info) {
        var tenantDomains = info.domains.stream().map(UUID::fromString).map(DomainId::new);
        return this.cache.fetchAll(Ownership.of(tenantDomains));
    }
}
