package pt.sensae.services.data.decoder.master.backend.infrastructure.endpoint.graphql.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.data.decoder.master.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;
import pt.sensae.services.data.decoder.master.backend.application.DataDecoderCollectorService;
import pt.sensae.services.data.decoder.master.backend.application.DataDecoderDTO;

import java.util.Set;
import java.util.stream.Collectors;

@DgsComponent
public class DataDecoderCollectorController {

    private final DataDecoderCollectorService service;

    public DataDecoderCollectorController(DataDecoderCollectorService service) {
        this.service = service;
    }

    @DgsQuery(field = "decoder")
    public Set<DataDecoderDTO> collect(@RequestHeader("Authorization") String auth) {
        return service.collectAll(AuthMiddleware.buildAccessToken(auth))
                .collect(Collectors.toSet());
    }
}
