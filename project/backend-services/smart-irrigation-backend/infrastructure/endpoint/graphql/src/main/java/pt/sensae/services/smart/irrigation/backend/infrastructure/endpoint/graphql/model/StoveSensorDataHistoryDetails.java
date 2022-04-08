package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model;

public record StoveSensorDataHistoryDetails(String id,
                                            Long reportedAt,
                                            TemperatureDataDetails temperature,
                                            HumidityDataDetails humidity) implements SensorDataHistoryDetails {
}
