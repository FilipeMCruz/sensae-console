package pt.sensae.services.data.processor.master.backend.infrastructure.endpoint.graphql.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.data.processor.master.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;
import pt.sensae.services.data.processor.master.backend.infrastructure.endpoint.graphql.model.SensorTypeIdDTOImpl;
import pt.sensae.services.data.processor.master.backend.application.SensorTypeIdDTO;
import pt.sensae.services.data.processor.master.backend.application.DataTransformationEraserService;

@DgsComponent
public class DataTransformationEraserController {

    private final DataTransformationEraserService service;

    public DataTransformationEraserController(DataTransformationEraserService service) {
        this.service = service;
    }

    @DgsMutation(field = "delete")
    public SensorTypeIdDTO delete(@InputArgument(value = "type") SensorTypeIdDTOImpl dto, @RequestHeader("Authorization") String auth) {
        return service.erase(dto, AuthMiddleware.buildAccessToken(auth));
    }
}
