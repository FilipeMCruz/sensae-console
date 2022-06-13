package pt.sensae.services.smart.irrigation.backend.application.services.irrigationZone;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.smart.irrigation.backend.application.auth.TokenExtractor;
import pt.sensae.services.smart.irrigation.backend.application.auth.UnauthorizedException;
import pt.sensae.services.smart.irrigation.backend.application.mapper.irrigationZone.IrrigationZoneCommandDTOMapper;
import pt.sensae.services.smart.irrigation.backend.application.mapper.irrigationZone.IrrigationZoneDTOMapper;
import pt.sensae.services.smart.irrigation.backend.application.model.irrigationZone.CreateIrrigationZoneCommandDTO;
import pt.sensae.services.smart.irrigation.backend.application.model.irrigationZone.IrrigationZoneDTO;
import pt.sensae.services.smart.irrigation.backend.domainservices.irrigationZone.CreateIrrigationZone;

@Service
public class CreateIrrigationZoneService {

    private final CreateIrrigationZone service;

    private final TokenExtractor authHandler;

    private final IrrigationZoneCommandDTOMapper commandMapper;

    private final IrrigationZoneDTOMapper mapper;

    public CreateIrrigationZoneService(CreateIrrigationZone service,
                                       TokenExtractor authHandler,
                                       IrrigationZoneCommandDTOMapper commandMapper,
                                       IrrigationZoneDTOMapper mapper) {
        this.service = service;
        this.authHandler = authHandler;
        this.commandMapper = commandMapper;
        this.mapper = mapper;
    }

    public IrrigationZoneDTO create(CreateIrrigationZoneCommandDTO dto, AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("smart_irrigation:garden:create"))
            throw new UnauthorizedException("No Permissions");

        var irrigationZone = service.create(commandMapper.toCommand(dto), extract);
        return mapper.toDto(irrigationZone);
    }
}
