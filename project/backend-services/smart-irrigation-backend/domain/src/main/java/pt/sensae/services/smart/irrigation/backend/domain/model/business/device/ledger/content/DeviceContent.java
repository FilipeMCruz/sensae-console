package pt.sensae.services.smart.irrigation.backend.domain.model.business.device.ledger.content;

import pt.sensae.services.smart.irrigation.backend.domain.model.GPSPoint;

import java.util.Objects;

public final class DeviceContent {
    private final DeviceName name;
    private final DeviceRecords records;
    private final GPSPoint coordinates;
    private RemoteControl remoteControl;

    public DeviceContent(DeviceName name, DeviceRecords records, GPSPoint coordinates, RemoteControl remoteControl) {
        this.name = name;
        this.records = records;
        this.coordinates = coordinates;
        this.remoteControl = remoteControl;
    }

    public boolean queueValveSwitch() {
        if (!remoteControl.value()) {
            return false;
        }
        if (this.remoteControl.isQueued()) {
            return false;
        }

        this.remoteControl = remoteControl.queue();
        return true;
    }

    public void resetQueue() {
        this.remoteControl = remoteControl.reset();
    }

    public DeviceName name() {
        return name;
    }

    public DeviceRecords records() {
        return records;
    }

    public GPSPoint coordinates() {
        return coordinates;
    }

    public RemoteControl remoteControl() {
        return remoteControl;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (DeviceContent) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.records, that.records) &&
                Objects.equals(this.coordinates, that.coordinates) &&
                Objects.equals(this.remoteControl, that.remoteControl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, records, coordinates, remoteControl);
    }

    @Override
    public String toString() {
        return "DeviceContent[" +
                "name=" + name + ", " +
                "records=" + records + ", " +
                "coordinates=" + coordinates + ", " +
                "remoteControl=" + remoteControl + ']';
    }
}
