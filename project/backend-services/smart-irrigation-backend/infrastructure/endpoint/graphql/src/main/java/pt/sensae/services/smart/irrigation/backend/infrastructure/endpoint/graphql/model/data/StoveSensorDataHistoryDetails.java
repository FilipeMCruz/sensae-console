package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data;

import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data.HumidityDataDetails;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data.SensorDataHistoryDetails;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data.TemperatureDataDetails;

public record StoveSensorDataHistoryDetails(String id,
                                            Long reportedAt,
                                            TemperatureDataDetails temperature,
                                            HumidityDataDetails humidity) implements SensorDataHistoryDetails {
}
