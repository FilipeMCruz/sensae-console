package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model;

public record ParkSensorDataDetails(GPSDataDetails gps,
                                    IlluminanceDataDetails illuminance,
                                    SoilMoistureDataDetails soilMoisture) implements SensorDataDetails {
}
