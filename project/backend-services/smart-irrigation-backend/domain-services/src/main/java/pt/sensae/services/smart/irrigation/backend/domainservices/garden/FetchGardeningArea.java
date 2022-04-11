package pt.sensae.services.smart.irrigation.backend.domainservices.garden;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningArea;

import java.util.stream.Stream;

@Service
public class FetchGardeningArea {

    private final GardeningAreaCache cache;

    public FetchGardeningArea(GardeningAreaCache cache) {
        this.cache = cache;
    }

    public Stream<GardeningArea> fetchAll() {
        return this.cache.fetchAll();
    }
}
