package pt.sensae.services.data.processor.master.backend.infrastructure.endpoint.graphql.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.data.processor.master.backend.application.DataTransformationCollectorService;
import pt.sensae.services.data.processor.master.backend.application.DataTransformationDTO;
import pt.sensae.services.data.processor.master.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;

import java.util.Set;
import java.util.stream.Collectors;

@DgsComponent
public class DataTransformationCollectorController {

    private final DataTransformationCollectorService service;

    public DataTransformationCollectorController(DataTransformationCollectorService service) {
        this.service = service;
    }

    @DgsQuery(field = "transformation")
    public Set<DataTransformationDTO> collect(@RequestHeader("Authorization") String auth) {
        return service.transformations(AuthMiddleware.buildAccessToken(auth))
                .collect(Collectors.toSet());
    }
}
