package pt.sensae.services.notification.management.backend.infrastructure.boot.auth;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pt.sensae.services.notification.management.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.auth.AccessTokenDTOImpl;
import pt.sensae.services.notification.management.backend.infrastructure.endpoint.graphql.auth.AuthTokenHandler;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

@Service
public class AuthTokenConfig implements AuthTokenHandler {

    @Value("${sensae.auth.pub.key.path}")
    public String PATH_RSA_PUB;

    @Value("${sensae.auth.issuer}")
    public String ISSUER;

    @Value("${sensae.auth.audience}")
    public String AUDIENCE;

    private PublicKey publicKey;

    @PostConstruct
    public void init() {
        try {
            byte[] publicKeyBytes = Files.readAllBytes(Path.of(PATH_RSA_PUB));
            publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Private Key File not Found");
        }
    }

    public Map<String, Object> decode(AccessTokenDTO token) {
        try {
            var dto = (AccessTokenDTOImpl) token;
            return Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .setAllowedClockSkewSeconds(60)
                    .requireAudience(AUDIENCE)
                    .requireIssuer(ISSUER)
                    .build()
                    .parseClaimsJws(dto.token).getBody();
        } catch (Exception ex) {
            throw new RuntimeException("Invalid Access Token");
        }
    }
}
