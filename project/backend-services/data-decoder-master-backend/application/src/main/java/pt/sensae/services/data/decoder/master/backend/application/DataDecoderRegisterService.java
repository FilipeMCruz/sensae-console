package pt.sensae.services.data.decoder.master.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.data.decoder.master.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.data.decoder.master.backend.application.auth.UnauthorizedException;
import pt.sensae.services.data.decoder.master.backend.application.auth.TokenExtractor;
import pt.sensae.services.data.decoder.master.backend.domain.LastTimeSeenDecoderRepository;
import pt.sensae.services.data.decoder.master.backend.domainservices.DataDecoderHoarder;

import java.time.Instant;

@Service
public class DataDecoderRegisterService {

    private final DataDecoderHoarder hoarder;

    private final DataDecoderMapper mapper;

    private final DataDecoderHandlerService publisher;

    private final TokenExtractor authHandler;

    private final LastTimeSeenDecoderRepository lastTimeSeenDecoderRepository;

    public DataDecoderRegisterService(DataDecoderHoarder hoarder,
                                      DataDecoderMapper mapper,
                                      DataDecoderHandlerService publisher,
                                      TokenExtractor authHandler,
                                      LastTimeSeenDecoderRepository lastTimeSeenDecoderRepository) {
        this.hoarder = hoarder;
        this.mapper = mapper;
        this.publisher = publisher;
        this.authHandler = authHandler;
        this.lastTimeSeenDecoderRepository = lastTimeSeenDecoderRepository;
    }

    public DataDecoderDTO register(DataDecoderDTO dto, AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("data_decoders:decoders:edit"))
            throw new UnauthorizedException("No Permissions");

        var hoard = hoarder.hoard(mapper.dtoToDomain(dto));

        var lastTimeSeen = lastTimeSeenDecoderRepository.find(hoard.id())
                .map(Instant::toEpochMilli)
                .orElse(0L);

        publisher.publishUpdate(hoard);
        return mapper.domainToDto(hoard, lastTimeSeen);
    }
}
