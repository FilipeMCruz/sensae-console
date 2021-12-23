package sharespot.services.dataprocessormaster.infrastructure.endpoint.graphql.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
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
    public DataTransformationDTO index(@InputArgument(value = "transformation") DataTransformationDTOImpl dto) {
        return service.register(dto);
    }
}
