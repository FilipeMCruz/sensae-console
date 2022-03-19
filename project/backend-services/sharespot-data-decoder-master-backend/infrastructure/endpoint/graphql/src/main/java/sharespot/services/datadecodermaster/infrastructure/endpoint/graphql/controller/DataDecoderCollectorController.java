package sharespot.services.datadecodermaster.infrastructure.endpoint.graphql.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import org.springframework.web.bind.annotation.RequestHeader;
import sharespot.services.datadecodermaster.infrastructure.endpoint.graphql.auth.AuthMiddleware;
import sharespot.services.datadecodermaster.application.DataDecoderCollectorService;
import sharespot.services.datadecodermaster.application.DataDecoderDTO;

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
        return service.transformations(AuthMiddleware.buildAccessToken(auth))
                .collect(Collectors.toSet());
    }
}
