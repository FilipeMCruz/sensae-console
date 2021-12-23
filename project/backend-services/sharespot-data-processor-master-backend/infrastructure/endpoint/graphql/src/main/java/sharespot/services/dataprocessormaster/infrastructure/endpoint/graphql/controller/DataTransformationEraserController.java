package sharespot.services.dataprocessormaster.infrastructure.endpoint.graphql.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import sharespot.services.dataprocessormaster.infrastructure.endpoint.graphql.model.SensorTypeIdDTOImpl;
import sharespot.services.dataprocessormaster.application.SensorTypeIdDTO;
import sharespot.services.dataprocessormaster.application.DataTransformationEraserService;

@DgsComponent
public class DataTransformationEraserController {

    private final DataTransformationEraserService service;

    public DataTransformationEraserController(DataTransformationEraserService service) {
        this.service = service;
    }

    @DgsMutation(field = "delete")
    public SensorTypeIdDTO delete(@InputArgument(value = "type") SensorTypeIdDTOImpl dto) {
        return service.erase(dto);
    }
}
