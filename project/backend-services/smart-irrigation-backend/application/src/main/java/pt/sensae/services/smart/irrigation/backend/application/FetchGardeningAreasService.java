package pt.sensae.services.smart.irrigation.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.smart.irrigation.backend.application.auth.TokenExtractor;
import pt.sensae.services.smart.irrigation.backend.application.mapper.garden.GardeningAreaDTOMapper;
import pt.sensae.services.smart.irrigation.backend.application.mapper.garden.GardeningAreaFiltersDTOMapper;
import pt.sensae.services.smart.irrigation.backend.application.model.garden.GardeningAreaDTO;
import pt.sensae.services.smart.irrigation.backend.application.model.garden.GardeningAreaFiltersDTO;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.FetchGardeningArea;

import java.util.stream.Stream;

@Service
public class FetchGardeningAreasService {

    private final FetchGardeningArea cache;

    private final TokenExtractor authHandler;

    private final GardeningAreaDTOMapper mapper;

    private final GardeningAreaFiltersDTOMapper filterMapper;

    public FetchGardeningAreasService(FetchGardeningArea cache, TokenExtractor authHandler, GardeningAreaDTOMapper mapper, GardeningAreaFiltersDTOMapper filterMapper) {
        this.cache = cache;
        this.authHandler = authHandler;
        this.mapper = mapper;
        this.filterMapper = filterMapper;
    }

    public Stream<GardeningAreaDTO> fetchAll(GardeningAreaFiltersDTO filters, AccessTokenDTO claims) {
        //TODO: Add new permissions
//        var extract = authHandler.extract(claims);
//        if (!extract.permissions.contains("smart_irrigation:garden:read"))
//            throw new UnauthorizedException("No Permissions");

        return cache.fetchAll(filterMapper.toCommand(filters)).map(mapper::toDto);
    }
}
