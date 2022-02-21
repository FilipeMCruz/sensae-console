package sharespot.services.identitymanagementbackend.infrastructure.boot.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SigningKeyResolverAdapter;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.security.Key;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Objects;

@Service
public class SigningKeyResolver extends SigningKeyResolverAdapter {

    private final RestTemplate client;

    public SigningKeyResolver() {
        this.client = new RestTemplate();
    }

    @Override
    public Key resolveSigningKey(JwsHeader jwsHeader, Claims claims) {
        try {
            byte[] keyBytes = Base64.decode(getMicrosoftPublicKey(jwsHeader.getKeyId()));
            var cer = CertificateFactory.getInstance("X.509")
                    .generateCertificate(new ByteArrayInputStream(keyBytes));
            return cer.getPublicKey();
        } catch (CertificateException e) {
            throw new RuntimeException("Invalid Identity Token");
        }
    }

    private String getMicrosoftPublicKey(String kid) {
        HttpEntity<String> requestEntity = new HttpEntity<>("");
        ResponseEntity<ObjectNode> responseEntity = client.exchange("https://login.microsoftonline.com/common/discovery/v2.0/keys", HttpMethod.GET, requestEntity, ObjectNode.class);

        var keys = Objects.requireNonNull(responseEntity.getBody()).path("keys");

        for (JsonNode arrayItem : keys) {
            if (arrayItem.get("kid").asText().equals(kid)) {
                return arrayItem.get("x5c").get(0).asText();
            }
        }
        throw new RuntimeException("Invalid Identity Token");
    }
}
