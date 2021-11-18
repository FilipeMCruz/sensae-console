package pt.sharespot.services.lgt92gpssensorgateway.infrastructure.endpoint.rest.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.sharespot.services.lgt92gpssensorgateway.application.LGT92SensorDataPublisherService;
import pt.sharespot.services.lgt92gpssensorgateway.model.dto.SensorData;

@RestController
@Api(description = "Data Gateway - REST Endpoint for LGT 92 Sensors data", tags = {"Sensors"})
public class LGT92SensorController {

    @Value("${auth.key}")
    private String secret;

    private final LGT92SensorDataPublisherService service;

    public LGT92SensorController(LGT92SensorDataPublisherService service) {
        this.service = service;
    }

    @CrossOrigin
    @PostMapping("/sensor-data")
    @ApiOperation(value = "Endpoint used to register new data collected by a LGT 92 sensor",
            notes = "This endpoint should be registered in helium console as the integration for LGT 92 sensor")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "When the Sensor data is registered with success"),
            @ApiResponse(code = 401, message = "When the request has an invalid value in the Authorization header"),
            @ApiResponse(code = 400, message = "When the request has incorrect headers or body")})
    public ResponseEntity<?> postLGT92SensorData(
            @ApiParam(value = "DTO with LGT 92 payload", required = true)
            @RequestBody SensorData sensorData,
            @RequestHeader("Authorization") String auth) {
        if (!secret.equalsIgnoreCase(auth))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");

        service.registerSensorData(sensorData);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
}
