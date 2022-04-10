package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data;

import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data.GPSDataDetails;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data.HumidityDataDetails;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data.SensorDataDetails;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data.TemperatureDataDetails;

public record StoveSensorDataDetails(GPSDataDetails gps, TemperatureDataDetails temperature,
                                     HumidityDataDetails humidity) implements SensorDataDetails {
}
