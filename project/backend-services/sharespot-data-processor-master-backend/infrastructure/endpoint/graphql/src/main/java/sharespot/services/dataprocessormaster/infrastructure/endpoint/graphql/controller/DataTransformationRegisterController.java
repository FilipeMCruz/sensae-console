package sharespot.services.dataprocessormaster.infrastructure.endpoint.graphql.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import sharespot.services.dataprocessormaster.infrastructure.endpoint.graphql.auth.AuthMiddleware;
import sharespot.services.dataprocessormaster.infrastructure.endpoint.graphql.model.DataTransformationDTOImpl;
import sharespot.services.dataprocessormaster.application.DataTransformationDTO;
import sharespot.services.dataprocessormaster.application.DataTransformationRegisterService;

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
