package pt.sensae.services.smart.irrigation.backend.domainservices.garden;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningArea;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.model.GardeningAreaFilters;

import java.util.function.Predicate;
import java.util.stream.Stream;

@Service
public class FetchGardeningArea {

    private final GardeningAreaCache cache;

    public FetchGardeningArea(GardeningAreaCache cache) {
        this.cache = cache;
    }

    public Stream<GardeningArea> fetchAll(GardeningAreaFilters filters) {
        return this.cache.fetchAll().filter(withFilters(filters));
    }

    private Predicate<GardeningArea> withFilters(GardeningAreaFilters filters) {
        return filters.type != null ? (area -> area.type().equals(filters.type)) : (area -> true);
    }
}
