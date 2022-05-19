package pt.sensae.services.smart.irrigation.backend.domainservices.garden;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.domain.auth.TenantInfo;
import pt.sensae.services.smart.irrigation.backend.domain.model.DomainId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningArea;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FetchGardeningArea {

    private final GardeningAreaCache cache;

    public FetchGardeningArea(GardeningAreaCache cache) {
        this.cache = cache;
    }

    public Stream<GardeningArea> fetchAll(TenantInfo info) {
        var tenantDomains = info.domains.stream().map(UUID::fromString).map(DomainId::new);
        return this.cache.fetchAll(tenantDomains);
    }
}
