package pt.sensae.services.smart.irrigation.backend.application.services.irrigationZone;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.smart.irrigation.backend.application.auth.TokenExtractor;
import pt.sensae.services.smart.irrigation.backend.application.auth.UnauthorizedException;
import pt.sensae.services.smart.irrigation.backend.application.mapper.irrigationZone.IrrigationZoneCommandDTOMapper;
import pt.sensae.services.smart.irrigation.backend.application.mapper.irrigationZone.IrrigationZoneDTOMapper;
import pt.sensae.services.smart.irrigation.backend.application.model.irrigationZone.IrrigationZoneDTO;
import pt.sensae.services.smart.irrigation.backend.application.model.irrigationZone.UpdateIrrigationZoneCommandDTO;
import pt.sensae.services.smart.irrigation.backend.domainservices.irrigationZone.UpdateIrrigationZone;

@Service
public class UpdateIrrigationZoneService {

    private final UpdateIrrigationZone service;

    private final TokenExtractor authHandler;

    private final IrrigationZoneCommandDTOMapper commandMapper;

    private final IrrigationZoneDTOMapper mapper;

    public UpdateIrrigationZoneService(UpdateIrrigationZone service,
                                       TokenExtractor authHandler,
                                       IrrigationZoneCommandDTOMapper commandMapper,
                                       IrrigationZoneDTOMapper mapper) {
        this.service = service;
        this.authHandler = authHandler;
        this.commandMapper = commandMapper;
        this.mapper = mapper;
    }

    public IrrigationZoneDTO update(UpdateIrrigationZoneCommandDTO dto, AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("smart_irrigation:garden:edit"))
            throw new UnauthorizedException("No Permissions");

        var update = service.update(commandMapper.toCommand(dto), extract);
        return mapper.toDto(update);
    }
}
