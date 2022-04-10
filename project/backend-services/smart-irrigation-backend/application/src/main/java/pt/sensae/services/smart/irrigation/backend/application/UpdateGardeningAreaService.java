package pt.sensae.services.smart.irrigation.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.smart.irrigation.backend.application.auth.TokenExtractor;
import pt.sensae.services.smart.irrigation.backend.application.mapper.garden.GardeningAreaCommandDTOMapper;
import pt.sensae.services.smart.irrigation.backend.application.mapper.garden.GardeningAreaDTOMapper;
import pt.sensae.services.smart.irrigation.backend.application.model.garden.GardeningAreaDTO;
import pt.sensae.services.smart.irrigation.backend.application.model.garden.UpdateGardeningAreaCommandDTO;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.UpdateGardeningArea;

@Service
public class UpdateGardeningAreaService {

    private final UpdateGardeningArea service;

    private final TokenExtractor authHandler;

    private final GardeningAreaCommandDTOMapper commandMapper;

    private final GardeningAreaDTOMapper mapper;

    public UpdateGardeningAreaService(UpdateGardeningArea service,
                                      TokenExtractor authHandler,
                                      GardeningAreaCommandDTOMapper commandMapper,
                                      GardeningAreaDTOMapper mapper) {
        this.service = service;
        this.authHandler = authHandler;
        this.commandMapper = commandMapper;
        this.mapper = mapper;
    }

    public GardeningAreaDTO update(UpdateGardeningAreaCommandDTO dto, AccessTokenDTO claims) {
//        var extract = authHandler.extract(claims);
//        if (!extract.permissions.contains("smart_irrigation:garden:update"))
//            throw new UnauthorizedException("No Permissions");

        var update = service.update(commandMapper.toCommand(dto));
        return mapper.toDto(update);
    }
}
