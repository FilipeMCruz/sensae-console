package sharespot.services.dataprocessormaster.infrastructure.endpoint.graphql.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import org.springframework.web.bind.annotation.RequestHeader;
import sharespot.services.dataprocessormaster.application.DataTransformationDTO;
import sharespot.services.dataprocessormaster.application.DataTransformationCollectorService;
import sharespot.services.dataprocessormaster.infrastructure.endpoint.graphql.auth.AuthMiddleware;

import java.util.Set;

@DgsComponent
public class DataTransformationCollectorController {

    private final DataTransformationCollectorService service;

    public DataTransformationCollectorController(DataTransformationCollectorService service) {
        this.service = service;
    }

    @DgsQuery(field = "transformation")
    public Set<DataTransformationDTO> collect(@RequestHeader("Authorization") String auth) {
        return service.transformations(AuthMiddleware.buildAccessToken(auth));
    }
}
