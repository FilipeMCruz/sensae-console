package sharespot.services.datadecodermaster.infrastructure.endpoint.graphql.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import sharespot.services.datadecodermaster.application.DataDecoderDTO;
import sharespot.services.datadecodermaster.application.DataDecoderRegisterService;
import sharespot.services.datadecodermaster.infrastructure.endpoint.graphql.auth.AuthMiddleware;
import sharespot.services.datadecodermaster.infrastructure.endpoint.graphql.model.DataDecoderDTOImpl;

@DgsComponent
public class DataDecoderRegisterController {

    private final DataDecoderRegisterService service;

    public DataDecoderRegisterController(DataDecoderRegisterService service) {
        this.service = service;
    }

    @DgsMutation(field = "index")
    public DataDecoderDTO index(@InputArgument(value = "decoder") DataDecoderDTOImpl dto, @RequestHeader("Authorization") String auth) {
        return service.register(dto, AuthMiddleware.buildAccessToken(auth));
    }
}
