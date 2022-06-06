package pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.controller.device;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import pt.sensae.services.identity.management.backend.application.model.device.DeviceIdDTO;
import pt.sensae.services.identity.management.backend.application.service.device.ViewDevicesInDomainService;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.AuthMiddleware;
import pt.sensae.services.identity.management.backend.infrastructure.endpoint.graphql.model.domain.ViewDomainDTOImpl;

import java.util.List;

@DgsComponent
public class DomainDevicesPresenterController {

    private final ViewDevicesInDomainService service;

    public DomainDevicesPresenterController(ViewDevicesInDomainService service) {
        this.service = service;
    }

    @DgsQuery(field = "viewDevicesInDomain")
    public List<DeviceIdDTO> fetch(@InputArgument(value = "domain") ViewDomainDTOImpl info, @RequestHeader("Authorization") String auth) {
        return service.fetch(info, AuthMiddleware.buildAccessToken(auth)).toList();
    }
}
