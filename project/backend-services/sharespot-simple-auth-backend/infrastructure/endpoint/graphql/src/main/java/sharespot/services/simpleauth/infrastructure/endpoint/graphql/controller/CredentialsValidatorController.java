package sharespot.services.simpleauth.infrastructure.endpoint.graphql.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import sharespot.services.simpleauth.application.CredentialsValidatorService;
import sharespot.services.simpleauth.infrastructure.endpoint.graphql.model.OutcomeDTOImpl;
import sharespot.services.simpleauth.infrastructure.endpoint.graphql.model.UserCredentialsDTOImpl;

@DgsComponent
public class CredentialsValidatorController {

    private final CredentialsValidatorService service;

    public CredentialsValidatorController(CredentialsValidatorService service) {
        this.service = service;
    }

    @DgsQuery(field = "credentials")
    public OutcomeDTOImpl validate(@InputArgument(value = "user") UserCredentialsDTOImpl dto) {
        return OutcomeDTOImpl.of(service.valid(dto));
    }
}
