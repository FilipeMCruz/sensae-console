package sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.mapper.tenant;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sharespot.services.identitymanagementbackend.application.mapper.tenant.TenantMapper;
import sharespot.services.identitymanagementbackend.application.model.tenant.AuthenticationDTO;
import sharespot.services.identitymanagementbackend.application.model.tenant.JWTTokenDTO;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityCommand;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityQuery;
import sharespot.services.identitymanagementbackend.domainservices.model.tenant.IdentityResult;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.tenant.AccessTokenDTO;
import sharespot.services.identitymanagementbackend.infrastructure.endpoint.graphql.model.tenant.AuthenticationDTOImpl;

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
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

//TODO: move jwt creation and extraction to other service
@Service
public class TenantMapperImpl implements TenantMapper {

    @Value("${sensae.auth.priv.key.path}")
    public String PATH_RSA_PRIV;

    @Value("${sensae.auth.pub.key.path}")
    public String PATH_RSA_PUB;

    @Value("${sensae.auth.issuer}")
    public String ISSUER;

    @Value("${sensae.auth.audience}")
    public String AUDIENCE;

    @Override
    public IdentityQuery dtoToCommand(AuthenticationDTO dto) {
        var authInfo = (AuthenticationDTOImpl) dto;
        var identityQuery = new IdentityQuery();
        identityQuery.email = authInfo.email;
        identityQuery.name = authInfo.name;
        identityQuery.oid = authInfo.oid;
        return identityQuery;
    }

    @Override
    public JWTTokenDTO commandToDto(IdentityResult result) {
        var now = new Date();
        var expiration = Date.from(now.toInstant().plus(30, ChronoUnit.MINUTES));

        String jws = Jwts.builder()
                .setSubject(result.oid.toString())
                .setIssuer(ISSUER)
                .setAudience(AUDIENCE)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .setNotBefore(now)
                .setId(UUID.randomUUID().toString())
                .setClaims(result.toClaims())
                .signWith(getPrivateKey())
                .compact();
        var accessToken = new AccessTokenDTO();
        accessToken.token = jws;
        return accessToken;
    }

    @Override
    public IdentityCommand dtoToCommand(JWTTokenDTO dto) {
        try {
            var identityCommand = new IdentityCommand();
            var token = (AccessTokenDTO) dto;
            var claims = Jwts.parserBuilder()
                    .setSigningKey(getPublicKey())
                    .setAllowedClockSkewSeconds(60)
                    .requireAudience(AUDIENCE)
                    .requireIssuer(ISSUER)
                    .build()
                    .parseClaimsJws(token.token).getBody();

            identityCommand.oid = claims.get("oid", UUID.class);
            identityCommand.email = claims.get("email", String.class);
            identityCommand.name = claims.get("name", String.class);
            identityCommand.domains = Arrays.stream(claims.get("domains", String[].class)).toList();

            return identityCommand;
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
