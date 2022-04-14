package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data;

import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data.IlluminanceDataDetails;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data.SensorDataHistoryDetails;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data.SoilMoistureDataDetails;

public record ParkSensorDataHistoryDetails(String id,
                                           Long reportedAt,
                                           IlluminanceDataDetails illuminance,
                                           SoilMoistureDataDetails soilMoisture) implements SensorDataHistoryDetails {
}
