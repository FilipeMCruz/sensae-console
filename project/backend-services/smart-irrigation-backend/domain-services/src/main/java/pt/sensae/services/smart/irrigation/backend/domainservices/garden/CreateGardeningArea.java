package pt.sensae.services.smart.irrigation.backend.domainservices.garden;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningArea;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.mapper.GardenMapper;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.model.CreateGardeningAreaCommand;

@Service
public class CreateGardeningArea {

    private final GardeningAreaCache cache;

    public CreateGardeningArea(GardeningAreaCache cache) {
        this.cache = cache;
    }

    public GardeningArea create(CreateGardeningAreaCommand createCommand) {
        var gardeningArea = GardenMapper.toModel(createCommand);
        cache.store(gardeningArea);
        return gardeningArea;
    }
}
