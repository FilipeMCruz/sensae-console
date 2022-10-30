package pt.sensae.services.data.decoder.master.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.data.decoder.master.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.data.decoder.master.backend.application.auth.UnauthorizedException;
import pt.sensae.services.data.decoder.master.backend.application.auth.TokenExtractor;
import pt.sensae.services.data.decoder.master.backend.domain.LastTimeSeenDecoderRepository;
import pt.sensae.services.data.decoder.master.backend.domainservices.DataDecoderCollector;

import java.time.Instant;
import java.util.stream.Stream;

@Service
public class DataDecoderCollectorService {

    private final DataDecoderCollector collector;

    private final DataDecoderMapper mapper;

    private final TokenExtractor authHandler;

    private final LastTimeSeenDecoderRepository lastTimeSeenDecoderRepository;

    public DataDecoderCollectorService(DataDecoderCollector collector,
                                       DataDecoderMapper mapper,
                                       TokenExtractor authHandler,
                                       LastTimeSeenDecoderRepository lastTimeSeenDecoderRepository) {
        this.collector = collector;
        this.mapper = mapper;
        this.authHandler = authHandler;
        this.lastTimeSeenDecoderRepository = lastTimeSeenDecoderRepository;
    }

    public Stream<DataDecoderDTO> collectAll(AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("data_decoders:decoders:read"))
            throw new UnauthorizedException("No Permissions");

        return collector.collect()
                .map(dec -> mapper.domainToDto(dec, lastTimeSeenDecoderRepository.find(dec.id())
                        .map(Instant::toEpochMilli)
                        .orElse(0L)));
    }
}
