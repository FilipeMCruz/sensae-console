package pt.sensae.services.data.decoder.master.backend.infrastructure.boot.auth;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pt.sensae.services.data.decoder.master.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.data.decoder.master.backend.infrastructure.endpoint.graphql.auth.AuthTokenHandler;

import java.util.Map;

@Service
@Profile("test")
public class AuthTokenConfigTest implements AuthTokenHandler {

    public Map<String, Object> decode(AccessTokenDTO token) {
        throw new UnsupportedOperationException();
    }
}
