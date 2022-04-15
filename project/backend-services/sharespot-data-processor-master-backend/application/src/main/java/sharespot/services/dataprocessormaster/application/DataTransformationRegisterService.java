package sharespot.services.dataprocessormaster.application;

import org.springframework.stereotype.Service;
import sharespot.services.dataprocessormaster.application.auth.AccessTokenDTO;
import sharespot.services.dataprocessormaster.application.auth.TokenExtractor;
import sharespot.services.dataprocessormaster.application.auth.UnauthorizedException;
import sharespot.services.dataprocessormaster.domainservices.DataTransformationHoarder;

@Service
public class DataTransformationRegisterService {

    private final DataTransformationHoarder hoarder;

    private final DataTransformationMapper mapper;

    private final DataTransformationHandlerService publisher;

    private final TokenExtractor authHandler;

    public DataTransformationRegisterService(DataTransformationHoarder hoarder,
                                             DataTransformationMapper mapper,
                                             DataTransformationHandlerService publisher,
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

        var deviceRecords = mapper.dtoToDomain(dto);
        hoarder.hoard(deviceRecords);
        publisher.publishUpdate(deviceRecords.getId());
        return dto;
    }
}
