package sharespot.services.data.decoder.master.application;

import org.springframework.stereotype.Service;
import sharespot.services.data.decoder.master.application.auth.TokenExtractor;
import sharespot.services.data.decoder.master.application.auth.UnauthorizedException;
import sharespot.services.data.decoder.master.application.auth.AccessTokenDTO;
import sharespot.services.datadecodermaster.domainservices.DataDecoderHoarder;

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
