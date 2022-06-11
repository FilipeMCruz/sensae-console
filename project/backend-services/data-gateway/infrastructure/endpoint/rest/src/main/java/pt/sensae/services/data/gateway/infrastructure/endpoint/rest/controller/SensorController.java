package pt.sensae.services.data.gateway.infrastructure.endpoint.rest.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.sensae.services.data.gateway.application.SensorDataPublisherService;

@RestController
@Api(description = "Data Gateway - REST Endpoint for Sensors data", tags = {"Sensors"})
public class SensorController {

    private final SensorDataPublisherService service;

    @Value("${sensae.auth.key}")
    private String secret;

    public SensorController(SensorDataPublisherService service) {
        this.service = service;
    }

    @CrossOrigin
    @PostMapping("/sensor-data/{infoType}/{sensorType}")
    @ApiOperation(value = "Endpoint used to register new data collected by a sensor",
            notes = "This endpoint should be registered in helium console as the integration for targeted 'sensorType' sensor")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "When the Sensor data is registered with success"),
            @ApiResponse(code = 401, message = "When the request has an invalid value in the Authorization header"),
            @ApiResponse(code = 400, message = "When the request has incorrect headers or body")})
    public ResponseEntity<?> postSensorData(
            @RequestHeader("Authorization") String auth,
            @ApiParam(value = "DTO with 'infoType' of 'sensorType' data", required = true)
            @RequestBody ObjectNode sensorData,
            @PathVariable String infoType,
            @PathVariable String sensorType) {
        if (!secret.equalsIgnoreCase(auth))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");

        service.registerSensorData(sensorData, "default", infoType, sensorType);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @CrossOrigin
    @PostMapping("/sensor-data/{channel}/{infoType}/{sensorType}")
    @ApiOperation(value = "Endpoint used to register new data collected by a sensor",
            notes = "This endpoint should be registered in helium console as the integration for default 'sensorType' sensor")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "When the Sensor data is registered with success"),
            @ApiResponse(code = 401, message = "When the request has an invalid value in the Authorization header"),
            @ApiResponse(code = 400, message = "When the request has incorrect headers or body")})
    public ResponseEntity<?> postSensorData(
            @RequestHeader("Authorization") String auth,
            @ApiParam(value = "DTO with 'infoType' of 'sensorType' data", required = true)
            @RequestBody ObjectNode sensorData,
            @PathVariable String channel,
            @PathVariable String infoType,
            @PathVariable String sensorType) {
        if (!secret.equalsIgnoreCase(auth))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");

        service.registerSensorData(sensorData, channel, infoType, sensorType);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
}
