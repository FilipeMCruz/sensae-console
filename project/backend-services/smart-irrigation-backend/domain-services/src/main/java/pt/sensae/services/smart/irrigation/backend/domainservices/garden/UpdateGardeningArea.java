package pt.sensae.services.smart.irrigation.backend.domainservices.garden;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningArea;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.mapper.GardenMapper;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.model.UpdateGardeningAreaCommand;

@Service
public class UpdateGardeningArea {

    private final GardeningAreaCache cache;

    public UpdateGardeningArea(GardeningAreaCache cache) {
        this.cache = cache;
    }

    public GardeningArea update(UpdateGardeningAreaCommand updateCommand) {
        var gardeningArea = GardenMapper.toModel(updateCommand);
        cache.store(gardeningArea);
        return gardeningArea;
    }
}
