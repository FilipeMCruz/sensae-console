package pt.sensae.services.smart.irrigation.backend.application.services.irrigationZone;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.smart.irrigation.backend.application.auth.TokenExtractor;
import pt.sensae.services.smart.irrigation.backend.application.auth.UnauthorizedException;
import pt.sensae.services.smart.irrigation.backend.application.mapper.irrigationZone.IrrigationZoneDTOMapper;
import pt.sensae.services.smart.irrigation.backend.application.model.irrigationZone.IrrigationZoneDTO;
import pt.sensae.services.smart.irrigation.backend.domainservices.irrigationZone.FetchIrrigationZone;

import java.util.stream.Stream;

@Service
public class FetchIrrigationZoneService {

    private final FetchIrrigationZone cache;

    private final TokenExtractor authHandler;

    private final IrrigationZoneDTOMapper mapper;

    public FetchIrrigationZoneService(FetchIrrigationZone cache, TokenExtractor authHandler, IrrigationZoneDTOMapper mapper) {
        this.cache = cache;
        this.authHandler = authHandler;
        this.mapper = mapper;
    }

    public Stream<IrrigationZoneDTO> fetchAll(AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("smart_irrigation:garden:read"))
            throw new UnauthorizedException("No Permissions");

        return cache.fetchAll(extract).map(mapper::toDto);
    }
}
