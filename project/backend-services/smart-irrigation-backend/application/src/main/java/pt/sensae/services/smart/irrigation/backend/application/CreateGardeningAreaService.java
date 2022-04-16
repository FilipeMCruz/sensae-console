package pt.sensae.services.smart.irrigation.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.smart.irrigation.backend.application.auth.TokenExtractor;
import pt.sensae.services.smart.irrigation.backend.application.auth.UnauthorizedException;
import pt.sensae.services.smart.irrigation.backend.application.mapper.garden.GardeningAreaCommandDTOMapper;
import pt.sensae.services.smart.irrigation.backend.application.mapper.garden.GardeningAreaDTOMapper;
import pt.sensae.services.smart.irrigation.backend.application.model.garden.CreateGardeningAreaCommandDTO;
import pt.sensae.services.smart.irrigation.backend.application.model.garden.GardeningAreaDTO;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.CreateGardeningArea;

@Service
public class CreateGardeningAreaService {

    private final CreateGardeningArea service;

    private final TokenExtractor authHandler;

    private final GardeningAreaCommandDTOMapper commandMapper;

    private final GardeningAreaDTOMapper mapper;

    public CreateGardeningAreaService(CreateGardeningArea service,
                                      TokenExtractor authHandler,
                                      GardeningAreaCommandDTOMapper commandMapper,
                                      GardeningAreaDTOMapper mapper) {
        this.service = service;
        this.authHandler = authHandler;
        this.commandMapper = commandMapper;
        this.mapper = mapper;
    }

    public GardeningAreaDTO create(CreateGardeningAreaCommandDTO dto, AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("smart_irrigation:garden:create"))
            throw new UnauthorizedException("No Permissions");

        var gardeningArea = service.create(commandMapper.toCommand(dto));
        return mapper.toDto(gardeningArea);
    }
}
