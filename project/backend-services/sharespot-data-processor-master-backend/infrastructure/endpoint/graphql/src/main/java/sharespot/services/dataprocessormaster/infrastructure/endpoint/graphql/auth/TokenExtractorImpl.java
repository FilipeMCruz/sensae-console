package sharespot.services.dataprocessormaster.infrastructure.endpoint.graphql.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import sharespot.services.dataprocessormaster.application.auth.AccessTokenDTO;
import sharespot.services.dataprocessormaster.application.auth.TokenExtractor;
import sharespot.services.dataprocessormaster.domainservices.auth.TenantInfo;

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
