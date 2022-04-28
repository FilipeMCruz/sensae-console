package pt.sensae.services.smart.irrigation.backend.application.services.garden;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.smart.irrigation.backend.application.auth.TokenExtractor;
import pt.sensae.services.smart.irrigation.backend.application.auth.UnauthorizedException;
import pt.sensae.services.smart.irrigation.backend.application.mapper.garden.GardeningAreaDTOMapper;
import pt.sensae.services.smart.irrigation.backend.application.model.garden.GardeningAreaDTO;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.FetchGardeningArea;

import java.util.stream.Stream;

@Service
public class FetchGardeningAreasService {

    private final FetchGardeningArea cache;

    private final TokenExtractor authHandler;

    private final GardeningAreaDTOMapper mapper;

    public FetchGardeningAreasService(FetchGardeningArea cache, TokenExtractor authHandler, GardeningAreaDTOMapper mapper) {
        this.cache = cache;
        this.authHandler = authHandler;
        this.mapper = mapper;
    }

    public Stream<GardeningAreaDTO> fetchAll(AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("smart_irrigation:garden:read"))
            throw new UnauthorizedException("No Permissions");

        return cache.fetchAll().map(mapper::toDto);
    }
}