package pt.sensae.services.smart.irrigation.backend.infrastructure.endpoint.graphql.model.data;

public record ValveDataDetails(GPSDataDetails gps,
                               ValveStatusDataDetails valve) implements SensorDataDetails {
}
