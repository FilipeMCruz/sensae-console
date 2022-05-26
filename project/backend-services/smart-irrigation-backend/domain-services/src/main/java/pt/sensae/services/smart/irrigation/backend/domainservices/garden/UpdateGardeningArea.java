package pt.sensae.services.smart.irrigation.backend.domainservices.garden;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.domain.auth.TenantInfo;
import pt.sensae.services.smart.irrigation.backend.domain.model.DomainId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningArea;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.Owners;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.mapper.GardenMapper;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.model.UpdateGardeningAreaCommand;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UpdateGardeningArea {

    private final GardeningAreaCache cache;

    public UpdateGardeningArea(GardeningAreaCache cache) {
        this.cache = cache;
    }

    public GardeningArea update(UpdateGardeningAreaCommand updateCommand, TenantInfo info) {
        var tenantDomains = info.domains.stream().map(UUID::fromString).map(DomainId::new).collect(Collectors.toSet());

        var gardeningArea = GardenMapper.toModel(updateCommand);

        cache.fetchByIds(Stream.of(gardeningArea.id())).findFirst().ifPresent(area -> {
            if (area.isOwner(new Owners(tenantDomains))) {
                cache.store(gardeningArea.withOwners(area.owners()));
            }
        });
        return gardeningArea;
    }
}
