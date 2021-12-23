package sharespot.services.dataprocessormaster.infrastructure.endpoint.graphql.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import sharespot.services.dataprocessormaster.application.DataTransformationDTO;
import sharespot.services.dataprocessormaster.application.DataTransformationCollectorService;

import java.util.Set;

@DgsComponent
public class DataTransformationCollectorController {

    private final DataTransformationCollectorService service;

    public DataTransformationCollectorController(DataTransformationCollectorService service) {
        this.service = service;
    }

    @DgsQuery(field = "transformation")
    public Set<DataTransformationDTO> collect() {
        return service.transformations();
    }
}
