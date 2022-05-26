package pt.sensae.services.smart.irrigation.backend.domainservices.garden;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.domain.auth.TenantInfo;
import pt.sensae.services.smart.irrigation.backend.domain.model.DomainId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningArea;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.Owners;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.mapper.GardenMapper;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.model.CreateGardeningAreaCommand;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CreateGardeningArea {

    private final GardeningAreaCache cache;

    public CreateGardeningArea(GardeningAreaCache cache) {
        this.cache = cache;
    }

    public GardeningArea create(CreateGardeningAreaCommand createCommand, TenantInfo info) {
        var tenantDomains = Stream.concat(info.domains.stream(), info.parentDomains.stream())
                .map(UUID::fromString)
                .map(DomainId::new)
                .collect(Collectors.toSet());

        var gardeningArea = GardenMapper.toModel(createCommand).withOwners(new Owners(tenantDomains));
        cache.store(gardeningArea);
        return gardeningArea;
    }
}
