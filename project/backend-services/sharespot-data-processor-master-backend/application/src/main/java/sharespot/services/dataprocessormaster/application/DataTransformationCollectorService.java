package sharespot.services.dataprocessormaster.application;

import org.springframework.stereotype.Service;
import sharespot.services.dataprocessormaster.application.auth.AccessTokenDTO;
import sharespot.services.dataprocessormaster.application.auth.TokenExtractor;
import sharespot.services.dataprocessormaster.application.auth.UnauthorizedException;
import sharespot.services.dataprocessormaster.domainservices.DataTransformationCollector;

import java.util.stream.Stream;

@Service
public class DataTransformationCollectorService {

    private final DataTransformationCollector collector;

    private final DataTransformationMapper mapper;

    private final TokenExtractor authHandler;

    public DataTransformationCollectorService(DataTransformationCollector collector,
                                              DataTransformationMapper mapper,
                                              TokenExtractor authHandler) {
        this.collector = collector;
        this.mapper = mapper;
        this.authHandler = authHandler;
    }

    public Stream<DataTransformationDTO> transformations(AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("data_transformations:transformations:read"))
            throw new UnauthorizedException("No Permissions");

        return collector.collect()
                .map(mapper::domainToDto);
    }
}
