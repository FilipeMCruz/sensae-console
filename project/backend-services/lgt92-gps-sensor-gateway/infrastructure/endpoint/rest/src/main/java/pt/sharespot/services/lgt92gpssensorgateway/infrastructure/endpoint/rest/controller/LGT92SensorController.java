package pt.sharespot.services.lgt92gpssensorgateway.infrastructure.endpoint.rest.controller;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.sharespot.services.lgt92gpssensorgateway.application.LGT92SensorDataPublisherService;
import pt.sharespot.services.lgt92gpssensorgateway.model.dto.sensor.lgt92sensor.LGT92SensorData;

@RestController
@Api(description = "Data Management - REST Endpoint for LGT 92 Sensors data", tags = {"Sensors"})
public class LGT92SensorController {

    @Value("${sharespot.helium.console.auth}")
    private String secret;

    Logger logger = LoggerFactory.getLogger(LGT92SensorController.class);

    private final LGT92SensorDataPublisherService service;

    public LGT92SensorController(LGT92SensorDataPublisherService service) {
        this.service = service;
    }

    @CrossOrigin
    @PostMapping("/lgt-92-sensors")
    @ApiOperation(value = "Endpoint used to register new data collected by a lgt 92 sensor",
            notes = "This endpoint should be registered in helium console as the integration for LGT 92 sensors")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "When the Sensor data is registered with success"),
            @ApiResponse(code = 400, message = "When the request has incorrect headers or body"),
            @ApiResponse(code = 401, message = "When the request has an invalid value in the Authorization header"),
            @ApiResponse(code = 409, message = "When the Sensor Data ID is already in the system")})
    public ResponseEntity<?> postLGT92SensorData(
            @ApiParam(value = "DTO with the basic sensor data and the LGT 92 decoded payload", required = true)
            @RequestBody LGT92SensorData sensorData,
            @RequestHeader("Authorization") String auth) {
        if (!secret.equalsIgnoreCase(auth))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");

        logger.info("New Data: " + sensorData.uuid);
        service.registerSensorData(sensorData);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
}
