package pt.sensae.services.device.commander.backend.domain.model.device;

public record Device(DeviceId id, DeviceName name, DeviceDownlink downlink) {
}
