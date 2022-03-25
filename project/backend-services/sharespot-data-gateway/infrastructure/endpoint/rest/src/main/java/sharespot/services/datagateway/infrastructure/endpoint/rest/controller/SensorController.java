package sharespot.services.datagateway.infrastructure.endpoint.rest.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sharespot.services.datagateway.application.SensorDataPublisherService;

@RestController
public class SensorController {

    private final SensorDataPublisherService service;

    @Value("${sharespot.auth.key}")
    private String secret;

    public SensorController(SensorDataPublisherService service) {
        this.service = service;
    }

    @CrossOrigin
    @PostMapping("/sensor-data/{infoType}/{sensorType}")
    public ResponseEntity<?> postSensorData(
            @RequestHeader("Authorization") String auth,
            @RequestBody ObjectNode sensorData,
            @PathVariable String infoType,
            @PathVariable String sensorType) {
        if (!secret.equalsIgnoreCase(auth))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");

        service.registerSensorData(sensorData, infoType, sensorType);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
}
