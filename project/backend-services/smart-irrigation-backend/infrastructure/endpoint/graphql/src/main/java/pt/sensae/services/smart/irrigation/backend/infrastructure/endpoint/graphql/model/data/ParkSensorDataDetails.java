package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data;

import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data.GPSDataDetails;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data.IlluminanceDataDetails;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data.SensorDataDetails;
import pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data.SoilMoistureDataDetails;

public record ParkSensorDataDetails(GPSDataDetails gps,
                                    IlluminanceDataDetails illuminance,
                                    SoilMoistureDataDetails soilMoisture) implements SensorDataDetails {
}
