package sharespot.services.datadecoder.domain;

public record DataDecoderNotification(SensorTypeId id, NotificationType type, DataDecoder information) {
}
