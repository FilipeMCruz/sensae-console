package pt.sensae.services.data.decoder.flow.domain;

public record DataDecoderNotification(SensorTypeId id, NotificationType type, DataDecoder information) {
}
