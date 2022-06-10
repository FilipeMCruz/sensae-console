package pt.sensae.services.smart.irrigation.backend.domainservices.irrigationZone;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.domain.auth.TenantInfo;
import pt.sensae.services.smart.irrigation.backend.domain.model.DomainId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone.IrrigationZone;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone.Owners;
import pt.sensae.services.smart.irrigation.backend.domainservices.irrigationZone.mapper.IrrigationZoneMapper;
import pt.sensae.services.smart.irrigation.backend.domainservices.irrigationZone.model.UpdateIrrigationZoneCommand;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UpdateIrrigationZone {

    private final IrrigationZoneCache cache;

    public UpdateIrrigationZone(IrrigationZoneCache cache) {
        this.cache = cache;
    }

    public IrrigationZone update(UpdateIrrigationZoneCommand updateCommand, TenantInfo info) {
        var tenantDomains = info.domains.stream().map(UUID::fromString).map(DomainId::new).collect(Collectors.toSet());

        var irrigationZone = IrrigationZoneMapper.toModel(updateCommand);

        cache.fetchByIds(Stream.of(irrigationZone.id())).findFirst().ifPresent(area -> {
            if (area.isOwner(new Owners(tenantDomains))) {
                cache.store(irrigationZone.withOwners(area.owners()));
            }
        });
        return irrigationZone;
    }
}
