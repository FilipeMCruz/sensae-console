package pt.sensae.services.device.management.master.backend.application.ownership;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pt.sensae.services.device.management.master.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.device.management.master.backend.application.auth.AccessTokenDTOImpl;
import pt.sensae.services.device.management.master.backend.domain.model.device.DeviceId;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class DeviceDomainCheckerService {
    private final RestTemplate client;

    @Value("${sensae.auth.server.url}")
    public String IDENTITY_MANAGEMENT_URL;

    public DeviceDomainCheckerService() {
        this.client = new RestTemplate();
    }

    public Stream<DeviceId> owns(AccessTokenDTO token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + ((AccessTokenDTOImpl) token).token);

        HttpEntity<String> requestEntity = new HttpEntity<>("", headers);
        ResponseEntity<DeviceIdDTOImpl[]> responseEntity = client.exchange(IDENTITY_MANAGEMENT_URL + "internal/devices", HttpMethod.GET, requestEntity, DeviceIdDTOImpl[].class);
        
        return Arrays.stream(Objects.requireNonNull(responseEntity.getBody()))
                .map(i -> i.oid)
                .map(UUID::fromString)
                .map(DeviceId::new);
    }
}
