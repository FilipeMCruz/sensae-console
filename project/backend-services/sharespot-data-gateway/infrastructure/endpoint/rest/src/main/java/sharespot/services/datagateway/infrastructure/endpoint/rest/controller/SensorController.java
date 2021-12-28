package sharespot.services.datagateway.infrastructure.endpoint.rest.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sharespot.services.datagateway.application.SensorDataPublisherService;

@RestController
@Api(description = "Data Gateway - REST Endpoint for Sensors data", tags = {"Sensors"})
public class SensorController {

    private final SensorDataPublisherService service;

    @Value("${sharespot.auth.key}")
    private String secret;

    public SensorController(SensorDataPublisherService service) {
        this.service = service;
    }

    @CrossOrigin
    @PostMapping("/sensor-data/{infoType}/{sensorType}")
    @ApiOperation(value = "Endpoint used to register new data collected by a lgt92 gps sensor",
            notes = "This endpoint should be registered in helium console as the integration for lgt92 gps sensor")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "When the Sensor data is registered with success"),
            @ApiResponse(code = 401, message = "When the request has an invalid value in the Authorization header"),
            @ApiResponse(code = 400, message = "When the request has incorrect headers or body")})
    public ResponseEntity<?> postSensorData(
            @RequestHeader("Authorization") String auth,
            @ApiParam(value = "DTO with lgt92 gps payload", required = true)
            @RequestBody ObjectNode sensorData,
            @PathVariable String infoType,
            @PathVariable String sensorType) {
        if (!secret.equalsIgnoreCase(auth))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");

        service.registerSensorData(sensorData, infoType, sensorType);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
}
