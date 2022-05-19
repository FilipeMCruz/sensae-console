package pt.sensae.services.smart.irrigation.backend.domainservices.garden;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.domain.auth.TenantInfo;
import pt.sensae.services.smart.irrigation.backend.domain.exceptions.NotFoundException;
import pt.sensae.services.smart.irrigation.backend.domain.model.DomainId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningArea;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningAreaId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.Owners;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.mapper.GardenMapper;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.model.DeleteGardeningAreaCommand;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.model.UpdateGardeningAreaCommand;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DeleteGardeningArea {

    private final GardeningAreaCache cache;

    public DeleteGardeningArea(GardeningAreaCache cache) {
        this.cache = cache;
    }

    public GardeningArea delete(DeleteGardeningAreaCommand deleteCommand, TenantInfo info) {
        var tenantDomains = info.domains.stream().map(UUID::fromString).map(DomainId::new).collect(Collectors.toSet());

        var areaId = GardeningAreaId.of(deleteCommand.id);
        var gardeningAreaToDelete = cache.fetchByIds(Stream.of(areaId)).findFirst();
        if (gardeningAreaToDelete.isPresent() && gardeningAreaToDelete.get().isOwner(new Owners(tenantDomains))) {
            cache.delete(GardeningAreaId.of(deleteCommand.id));
            return gardeningAreaToDelete.get();
        } else {
            throw new NotFoundException("Garden not found");
        }
    }
}
