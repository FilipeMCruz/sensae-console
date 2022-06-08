package pt.sensae.services.data.processor.master.backend.infrastructure.endpoint.graphql.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.data.processor.master.backend.infrastructure.endpoint.graphql.auth.AuthMiddleware;
import pt.sensae.services.data.processor.master.backend.infrastructure.endpoint.graphql.model.DataTransformationDTOImpl;
import pt.sensae.services.data.processor.master.backend.application.DataTransformationDTO;
import pt.sensae.services.data.processor.master.backend.application.DataTransformationRegisterService;

@DgsComponent
public class DataTransformationRegisterController {

    private final DataTransformationRegisterService service;

    public DataTransformationRegisterController(DataTransformationRegisterService service) {
        this.service = service;
    }

    @DgsMutation(field = "index")
    public DataTransformationDTO index(@InputArgument(value = "transformation") DataTransformationDTOImpl dto, @RequestHeader("Authorization") String auth) {
        return service.register(dto, AuthMiddleware.buildAccessToken(auth));
    }
}
