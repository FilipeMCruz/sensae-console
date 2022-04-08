package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model;

public record ParkSensorDataHistoryDetails(String id,
                                           Long reportedAt,
                                           IlluminanceDataDetails illuminance,
                                           SoilMoistureDataDetails soilMoisture) implements SensorDataHistoryDetails {
}
