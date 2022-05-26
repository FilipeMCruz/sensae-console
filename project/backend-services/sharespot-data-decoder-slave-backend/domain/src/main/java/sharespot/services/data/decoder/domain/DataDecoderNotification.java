package sharespot.services.data.decoder.domain;

public record DataDecoderNotification(SensorTypeId id, NotificationType type, DataDecoder information) {
}
