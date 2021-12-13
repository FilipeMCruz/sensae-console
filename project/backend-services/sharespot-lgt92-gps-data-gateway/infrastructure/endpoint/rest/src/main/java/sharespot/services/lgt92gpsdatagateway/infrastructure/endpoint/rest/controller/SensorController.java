package sharespot.services.lgt92gpsdatagateway.infrastructure.endpoint.rest.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sharespot.services.lgt92gpsdatagateway.application.SensorDataPublisherService;

@RestController
@Api(description = "Data Gateway - REST Endpoint for lgt92 Sensors data", tags = {"Sensors"})
public class SensorController {

    private final SensorDataPublisherService service;

    public SensorController(SensorDataPublisherService service) {
        this.service = service;
    }

    @CrossOrigin
    @PostMapping("/sensor-data")
    @ApiOperation(value = "Endpoint used to register new data collected by a lgt92 gps sensor",
            notes = "This endpoint should be registered in helium console as the integration for lgt92 gps sensor")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "When the Sensor data is registered with success"),
            @ApiResponse(code = 400, message = "When the request has incorrect headers or body")})
    public ResponseEntity<?> postSensorData(
            @ApiParam(value = "DTO with lgt92 gps payload", required = true)
            @RequestBody ObjectNode sensorData) {
        service.registerSensorData(sensorData);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
}
