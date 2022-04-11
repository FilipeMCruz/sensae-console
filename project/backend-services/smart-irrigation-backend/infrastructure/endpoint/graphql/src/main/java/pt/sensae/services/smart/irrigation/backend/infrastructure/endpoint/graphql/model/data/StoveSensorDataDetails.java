package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data;

public record StoveSensorDataDetails(GPSDataDetails gps, TemperatureDataDetails temperature,
                                     HumidityDataDetails humidity) implements SensorDataDetails {
}
