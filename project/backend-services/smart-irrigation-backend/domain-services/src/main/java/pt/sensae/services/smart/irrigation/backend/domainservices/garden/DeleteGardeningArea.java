package pt.sensae.services.smart.irrigation.backend.domainservices.garden;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.domain.exceptions.NotFoundException;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningArea;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningAreaId;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.mapper.GardenMapper;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.model.DeleteGardeningAreaCommand;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.model.UpdateGardeningAreaCommand;

import java.util.stream.Stream;

@Service
public class DeleteGardeningArea {

    private final GardeningAreaCache cache;

    public DeleteGardeningArea(GardeningAreaCache cache) {
        this.cache = cache;
    }

    public GardeningArea delete(DeleteGardeningAreaCommand deleteCommand) {
        var areaId = GardeningAreaId.of(deleteCommand.id);
        var gardeningAreaStream = cache.fetchByIds(Stream.of(areaId)).findFirst();
        if (gardeningAreaStream.isPresent()) {
            cache.delete(GardeningAreaId.of(deleteCommand.id));
            return gardeningAreaStream.get();
        } else {
            throw new NotFoundException("Garden not found");
        }
    }
}
