package pt.sensae.services.data.processor.master.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.data.processor.master.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.data.processor.master.backend.application.auth.TokenExtractor;
import pt.sensae.services.data.processor.master.backend.application.auth.UnauthorizedException;
import pt.sensae.services.data.processor.master.backend.domainservices.DataTransformationHoarder;

@Service
public class DataTransformationRegisterService {

    private final DataTransformationHoarder hoarder;

    private final DataTransformationMapper mapper;

    private final DataTransformationEventHandlerService publisher;

    private final TokenExtractor authHandler;

    public DataTransformationRegisterService(DataTransformationHoarder hoarder,
                                             DataTransformationMapper mapper,
                                             DataTransformationEventHandlerService publisher,
                                             TokenExtractor authHandler) {
        this.hoarder = hoarder;
        this.mapper = mapper;
        this.publisher = publisher;
        this.authHandler = authHandler;
    }

    public DataTransformationDTO register(DataTransformationDTO dto, AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("data_transformations:transformations:edit"))
            throw new UnauthorizedException("No Permissions");

        var hoard = hoarder.hoard(mapper.dtoToDomain(dto));
        publisher.publishUpdate(hoard);
        return dto;
    }
}
