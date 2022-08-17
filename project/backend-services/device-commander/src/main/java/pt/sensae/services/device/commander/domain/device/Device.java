package pt.sensae.services.device.commander.domain.device;

public record Device(DeviceId id, DeviceName name, DeviceDownlink downlink) {

    public static Device empty(DeviceId id) {
        return new Device(id, new DeviceName(""), new DeviceDownlink(""));
    }
}
