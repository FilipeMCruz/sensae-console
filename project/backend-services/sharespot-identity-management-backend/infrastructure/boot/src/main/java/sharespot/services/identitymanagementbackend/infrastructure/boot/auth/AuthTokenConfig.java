package sharespot.services.identitymanagementbackend.infrastructure.boot.auth;

import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.model.tenant.AccessTokenDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.IdentityTokenDTO;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.AuthTokenHandler;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.tenant.AccessTokenDTOImpl;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.tenant.IdentityTokenDTOImpl;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthTokenConfig implements AuthTokenHandler {

    @Value("${sensae.auth.priv.key.path}")
    public String PATH_RSA_PRIV;

    @Value("${sensae.auth.pub.key.path}")
    public String PATH_RSA_PUB;

    @Value("${sensae.auth.issuer}")
    public String ISSUER;

    @Value("${sensae.auth.audience}")
    public String AUDIENCE;

    @Value("${sensae.auth.external.issuer}")
    public String EXTERNAL_ISSUER;

    @Value("${sensae.auth.external.audience}")
    public String EXTERNAL_AUDIENCE;

    public Logger logger = LoggerFactory.getLogger(AuthTokenConfig.class);

    private PrivateKey privateKey;

    private PublicKey publicKey;

    private SigningKeyResolver keyResolver;

    public AuthTokenConfig() {
        this.keyResolver = new SigningKeyResolver();
    }

    @PostConstruct
    public void init() {
        try {
            byte[] privateKeyBytes = Files.readAllBytes(Path.of(PATH_RSA_PRIV));
            privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));

            byte[] publicKeyBytes = Files.readAllBytes(Path.of(PATH_RSA_PUB));
            publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.warn(e.getMessage());
            throw new RuntimeException("Private Key File not Found");
        }
    }

    public AccessTokenDTO encode(Map<String, Object> identity) {
        var now = new Date();
        var expiration = Date.from(now.toInstant().plus(30, ChronoUnit.MINUTES));

        String jws = Jwts.builder()
                .setClaims(identity)
                .setSubject(identity.get("oid").toString())
                .setIssuer(ISSUER)
                .setAudience(AUDIENCE)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .setNotBefore(now)
                .setId(UUID.randomUUID().toString())
                .signWith(privateKey)
                .compact();
        var accessToken = new AccessTokenDTOImpl();
        accessToken.token = jws;
        return accessToken;
    }

    @Override
    public Map<String, Object> decode(IdentityTokenDTO token) {
        try {
            var dto = (IdentityTokenDTOImpl) token;
            return Jwts.parserBuilder()
                    .setSigningKeyResolver(keyResolver)
                    .setAllowedClockSkewSeconds(60)
                    .requireAudience(EXTERNAL_AUDIENCE)
                    .requireIssuer(EXTERNAL_ISSUER)
                    .build()
                    .parseClaimsJws(dto.token).getBody();
        } catch (Exception ex) {
            throw new RuntimeException("Invalid Identity Token", ex);
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
