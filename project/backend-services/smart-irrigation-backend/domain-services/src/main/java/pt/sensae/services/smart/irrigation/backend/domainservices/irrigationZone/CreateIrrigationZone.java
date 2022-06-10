package pt.sensae.services.smart.irrigation.backend.domainservices.irrigationZone;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.domain.auth.TenantInfo;
import pt.sensae.services.smart.irrigation.backend.domain.model.DomainId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone.IrrigationZone;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone.Owners;
import pt.sensae.services.smart.irrigation.backend.domainservices.irrigationZone.mapper.IrrigationZoneMapper;
import pt.sensae.services.smart.irrigation.backend.domainservices.irrigationZone.model.CreateIrrigationZoneCommand;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CreateIrrigationZone {

    private final IrrigationZoneCache cache;

    public CreateIrrigationZone(IrrigationZoneCache cache) {
        this.cache = cache;
    }

    public IrrigationZone create(CreateIrrigationZoneCommand createCommand, TenantInfo info) {
        var tenantDomains = Stream.concat(info.domains.stream(), info.parentDomains.stream())
                .map(UUID::fromString)
                .map(DomainId::new)
                .collect(Collectors.toSet());

        var irrigationZone = IrrigationZoneMapper.toModel(createCommand).withOwners(new Owners(tenantDomains));
        cache.store(irrigationZone);
        return irrigationZone;
    }
}
