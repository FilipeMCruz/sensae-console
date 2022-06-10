package pt.sensae.services.smart.irrigation.backend.domainservices.irrigationZone;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.domain.auth.TenantInfo;
import pt.sensae.services.smart.irrigation.backend.domain.exceptions.NotFoundException;
import pt.sensae.services.smart.irrigation.backend.domain.model.DomainId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone.IrrigationZone;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone.IrrigationZoneId;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.irrigationZone.Owners;
import pt.sensae.services.smart.irrigation.backend.domainservices.irrigationZone.model.DeleteIrrigationZoneCommand;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DeleteIrrigationZone {

    private final IrrigationZoneCache cache;

    public DeleteIrrigationZone(IrrigationZoneCache cache) {
        this.cache = cache;
    }

    public IrrigationZone delete(DeleteIrrigationZoneCommand deleteCommand, TenantInfo info) {
        var tenantDomains = info.domains.stream().map(UUID::fromString).map(DomainId::new).collect(Collectors.toSet());

        var areaId = IrrigationZoneId.of(deleteCommand.id);
        var irrigationZoneToDelete = cache.fetchByIds(Stream.of(areaId)).findFirst();
        if (irrigationZoneToDelete.isPresent() && irrigationZoneToDelete.get().isOwner(new Owners(tenantDomains))) {
            cache.delete(IrrigationZoneId.of(deleteCommand.id));
            return irrigationZoneToDelete.get();
        } else {
            throw new NotFoundException("Irrigation Zone not found");
        }
    }
}
