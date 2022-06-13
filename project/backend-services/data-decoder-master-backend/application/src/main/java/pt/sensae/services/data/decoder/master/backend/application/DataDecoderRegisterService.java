package pt.sensae.services.data.decoder.master.backend.application;

import org.springframework.stereotype.Service;
import pt.sensae.services.data.decoder.master.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.data.decoder.master.backend.application.auth.UnauthorizedException;
import pt.sensae.services.data.decoder.master.backend.application.auth.TokenExtractor;
import pt.sensae.services.data.decoder.master.backend.domainservices.DataDecoderHoarder;

@Service
public class DataDecoderRegisterService {

    private final DataDecoderHoarder hoarder;

    private final DataDecoderMapper mapper;

    private final DataDecoderHandlerService publisher;

    private final TokenExtractor authHandler;

    public DataDecoderRegisterService(DataDecoderHoarder hoarder,
                                      DataDecoderMapper mapper,
                                      DataDecoderHandlerService publisher,
                                      TokenExtractor authHandler) {
        this.hoarder = hoarder;
        this.mapper = mapper;
        this.publisher = publisher;
        this.authHandler = authHandler;
    }

    public DataDecoderDTO register(DataDecoderDTO dto, AccessTokenDTO claims) {
        var extract = authHandler.extract(claims);
        if (!extract.permissions.contains("data_decoders:decoders:edit"))
            throw new UnauthorizedException("No Permissions");

        var hoard = hoarder.hoard(mapper.dtoToDomain(dto));
        publisher.publishUpdate(hoard);
        return dto;
    }
}
