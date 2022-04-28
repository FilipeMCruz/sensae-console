package pt.sensae.services.smart.irrigation.backend.application.services.garden;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.smart.irrigation.backend.application.auth.TokenExtractor;
import pt.sensae.services.smart.irrigation.backend.application.auth.UnauthorizedException;
import pt.sensae.services.smart.irrigation.backend.application.mapper.garden.GardeningAreaCommandDTOMapper;
import pt.sensae.services.smart.irrigation.backend.application.mapper.garden.GardeningAreaDTOMapper;
import pt.sensae.services.smart.irrigation.backend.application.model.garden.DeleteGardeningAreaCommandDTO;
import pt.sensae.services.smart.irrigation.backend.application.model.garden.GardeningAreaDTO;
import pt.sensae.services.smart.irrigation.backend.application.model.garden.UpdateGardeningAreaCommandDTO;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.garden.GardeningArea;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.DeleteGardeningArea;
import pt.sensae.services.smart.irrigation.backend.domainservices.garden.UpdateGardeningArea;

@Service
public class DeleteGardeningAreaService {

    private final DeleteGardeningArea service;

    private final TokenExtractor authHandler;

    private final GardeningAreaCommandDTOMapper commandMapper;

    private final GardeningAreaDTOMapper mapper;

    public DeleteGardeningAreaService(DeleteGardeningArea service,
                                      TokenExtractor authHandler,
                                      GardeningAreaCommandDTOMapper commandMapper,
                                      GardeningAreaDTOMapper mapper) {
        this.service = service;
        this.authHandler = authHandler;
        this.commandMapper = commandMapper;
        this.mapper = mapper;
    }

    public GardeningAreaDTO delete(DeleteGardeningAreaCommandDTO dto, AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("smart_irrigation:garden:delete"))
            throw new UnauthorizedException("No Permissions");

        var deleted = service.delete(commandMapper.toCommand(dto));
        return mapper.toDto(deleted);

    }
}
