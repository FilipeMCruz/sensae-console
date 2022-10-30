package pt.sensae.services.notification.management.backend.infrastructure.boot;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pt.sensae.services.notification.management.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.auth.AuthTokenHandler;

import java.util.Map;

@Service
@Profile("test")
public class AuthTokenConfigTest implements AuthTokenHandler {

    public Map<String, Object> decode(AccessTokenDTO token) {
        throw new UnsupportedOperationException();
    }
}
