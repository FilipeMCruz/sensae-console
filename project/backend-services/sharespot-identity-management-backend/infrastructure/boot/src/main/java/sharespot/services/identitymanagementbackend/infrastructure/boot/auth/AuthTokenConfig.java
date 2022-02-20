package sharespot.services.identitymanagementbackend.infrastructure.boot.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.model.tenant.JWTTokenDTO;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.AuthTokenHandler;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.tenant.AccessTokenDTOImpl;

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
import java.util.*;

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

    public JWTTokenDTO encode(Map<String, Object> identity) {
        var now = new Date();
        var expiration = Date.from(now.toInstant().plus(30, ChronoUnit.MINUTES));

        String jws = Jwts.builder()
                .setSubject(identity.get("oid").toString())
                .setIssuer(ISSUER)
                .setAudience(AUDIENCE)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .setNotBefore(now)
                .setId(UUID.randomUUID().toString())
                .setClaims(identity)
                .signWith(getPrivateKey())
                .compact();
        var accessToken = new AccessTokenDTOImpl();
        accessToken.token = jws;
        return accessToken;
    }

    public Claims decode(JWTTokenDTO token) {
        try {
            var dto = (AccessTokenDTOImpl) token;
            return Jwts.parserBuilder()
                    .setSigningKey(getPublicKey())
                    .setAllowedClockSkewSeconds(60)
                    .requireAudience(AUDIENCE)
                    .requireIssuer(ISSUER)
                    .build()
                    .parseClaimsJws(dto.token).getBody();
        } catch (Exception ex) {
            throw new RuntimeException("Invalid Access Token");
        }
    }

    private PrivateKey getPrivateKey() {
        try {
            String rsaPrivateKey = Arrays.toString(Files.readAllBytes(Path.of(PATH_RSA_PRIV)))
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "");

            return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(rsaPrivateKey)));
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Private Key File not Found");
        }
    }

    private PublicKey getPublicKey() {
        try {
            String rsaPublicKey = Arrays.toString(Files.readAllBytes(Path.of(PATH_RSA_PUB)))
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "");

            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(rsaPublicKey)));
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Private Key File not Found");
        }
    }
}
