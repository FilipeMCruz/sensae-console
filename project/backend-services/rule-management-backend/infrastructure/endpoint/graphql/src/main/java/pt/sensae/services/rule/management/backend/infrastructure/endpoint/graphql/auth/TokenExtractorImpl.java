package pt.sensae.services.rule.management.backend.infrastructure.endpoint.graphql.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pt.sensae.services.rule.management.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.rule.management.backend.application.auth.TokenExtractor;
import pt.sensae.services.rule.management.backend.domainservices.auth.TenantInfo;

@Service
public class TokenExtractorImpl implements TokenExtractor {

    private final AuthTokenHandler authHandler;

    private final ObjectMapper mapper;

    public TokenExtractorImpl(AuthTokenHandler authHandler, ObjectMapper mapper) {
        this.authHandler = authHandler;
        this.mapper = mapper;
    }

    @Override
    public TenantInfo extract(AccessTokenDTO dto) {
        var claims = authHandler.decode(dto);
        return mapper.convertValue(claims, TenantInfo.class);
    }
}
