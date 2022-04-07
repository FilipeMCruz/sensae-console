package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model;

public record StoveSensorDataDetails(GPSDataDetails gps, TemperatureDataDetails temperature,
                                     HumidityDataDetails humidity) implements SensorDataDetails {
}
