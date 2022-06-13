package pt.sensae.services.data.decoder.master.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.data.decoder.master.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.data.decoder.master.backend.application.auth.UnauthorizedException;
import pt.sensae.services.data.decoder.master.backend.application.auth.TokenExtractor;
import pt.sensae.services.data.decoder.master.backend.domainservices.DataDecoderCollector;

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
        if (!extract.permissions.contains("data_decoders:decoders:read"))
            throw new UnauthorizedException("No Permissions");

        return collector.collect()
                .map(mapper::domainToDto);
    }
}
