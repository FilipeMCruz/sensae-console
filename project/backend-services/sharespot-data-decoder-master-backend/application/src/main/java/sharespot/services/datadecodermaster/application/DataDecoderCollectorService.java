package sharespot.services.datadecodermaster.application;

import org.springframework.stereotype.Service;
import sharespot.services.datadecodermaster.application.auth.TokenExtractor;
import sharespot.services.datadecodermaster.application.auth.AccessTokenDTO;
import sharespot.services.datadecodermaster.application.auth.UnauthorizedException;
import sharespot.services.datadecodermaster.domainservices.DataDecoderCollector;

import java.util.stream.Stream;

@Service
public class DataDecoderCollectorService {

    private final DataDecoderCollector collector;

    private final DataDecoderMapper mapper;

    private final TokenExtractor authHandler;

    public DataDecoderCollectorService(DataDecoderCollector collector,
                                       DataDecoderMapper mapper,
                                       TokenExtractor authHandler) {
        this.collector = collector;
        this.mapper = mapper;
        this.authHandler = authHandler;
    }

    public Stream<DataDecoderDTO> transformations(AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("data_transformations:transformations:read"))
            throw new UnauthorizedException("No Permissions");

        return collector.collect()
                .map(mapper::domainToDto);
    }
}
