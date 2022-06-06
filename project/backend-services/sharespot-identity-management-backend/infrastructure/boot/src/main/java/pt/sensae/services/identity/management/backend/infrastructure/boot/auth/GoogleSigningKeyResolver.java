package pt.sensae.services.identity.management.backend.infrastructure.boot.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SigningKeyResolverAdapter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Objects;

@Service
public class GoogleSigningKeyResolver extends SigningKeyResolverAdapter {

    private final RestTemplate client;

    public GoogleSigningKeyResolver() {
        this.client = new RestTemplate();
    }

    @Override
    public Key resolveSigningKey(JwsHeader jwsHeader, Claims claims) {
        try {
            return getGooglePublicKey(jwsHeader.getKeyId());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Invalid Identity Token");
        }
    }

    private PublicKey getGooglePublicKey(String kid) throws NoSuchAlgorithmException, InvalidKeySpecException {
        HttpEntity<String> requestEntity = new HttpEntity<>("");
        ResponseEntity<ObjectNode> responseEntity = client.exchange("https://www.googleapis.com/oauth2/v3/certs", HttpMethod.GET, requestEntity, ObjectNode.class);

        var keys = Objects.requireNonNull(responseEntity.getBody()).path("keys");

        for (JsonNode arrayItem : keys) {
            if (arrayItem.get("kid").asText().equals(kid)) {
                var modulus = Base64Utils.decodeFromUrlSafeString(arrayItem.get("n").asText());
                var exponent = Base64Utils.decodeFromUrlSafeString(arrayItem.get("e").asText());
                BigInteger bigExponent = new BigInteger(1, exponent);
                BigInteger bigModulus = new BigInteger(1, modulus);
                return KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(bigModulus, bigExponent));
            }
        }
        throw new RuntimeException("Invalid Identity Token");
    }
}
