package sharespot.services.simpleauth.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CredentialsValidatorService {

    @Value("${sharespot.simple.auth.user.name}")
    private String user;

    @Value("${sharespot.simple.auth.user.secret}")
    private String secret;

    public boolean valid(UserCredentialsDTO userCredentials) {
        return user.equals(userCredentials.getName()) && secret.equals(userCredentials.getSecret());
    }
}
