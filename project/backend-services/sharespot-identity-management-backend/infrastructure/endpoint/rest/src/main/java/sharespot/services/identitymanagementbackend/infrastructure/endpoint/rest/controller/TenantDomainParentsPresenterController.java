package sharespot.services.identitymanagementbackend.infrastructure.endpoint.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import sharespot.services.identitymanagementbackend.application.model.domain.DomainIdDTO;
import sharespot.services.identitymanagementbackend.application.service.domain.ViewTenantDomainService;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.rest.AuthMiddleware;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class TenantDomainParentsPresenterController {

    private final ViewTenantDomainService service;

    public TenantDomainParentsPresenterController(ViewTenantDomainService service) {
        this.service = service;
    }

    @GetMapping(path = "/internal/domains")
    public ResponseEntity<Set<DomainIdDTO>> fetch(@RequestHeader("Authorization") String auth) {
        return new ResponseEntity<>(service.fetch(AuthMiddleware.buildAccessToken(auth))
                .collect(Collectors.toSet()), HttpStatus.OK);
    }
}
