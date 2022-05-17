package sharespot.services.data.decoder.master.infrastructure.endpoint.graphql.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import sharespot.services.data.decoder.master.infrastructure.endpoint.graphql.auth.AuthMiddleware;
import sharespot.services.data.decoder.master.infrastructure.endpoint.graphql.model.SensorTypeIdDTOImpl;
import sharespot.services.data.decoder.master.application.SensorTypeIdDTO;
import sharespot.services.data.decoder.master.application.DataDecoderEraserService;

@DgsComponent
public class DataDecoderEraserController {

    private final DataDecoderEraserService service;

    public DataDecoderEraserController(DataDecoderEraserService service) {
        this.service = service;
    }

    @DgsMutation(field = "delete")
    public SensorTypeIdDTO delete(@InputArgument(value = "type") SensorTypeIdDTOImpl dto, @RequestHeader("Authorization") String auth) {
        return service.erase(dto, AuthMiddleware.buildAccessToken(auth));
    }
}
