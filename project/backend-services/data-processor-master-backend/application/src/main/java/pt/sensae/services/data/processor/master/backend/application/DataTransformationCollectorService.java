package pt.sensae.services.data.processor.master.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.data.processor.master.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.data.processor.master.backend.application.auth.TokenExtractor;
import pt.sensae.services.data.processor.master.backend.application.auth.UnauthorizedException;
import pt.sensae.services.data.processor.master.backend.domainservices.DataTransformationCollector;

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
