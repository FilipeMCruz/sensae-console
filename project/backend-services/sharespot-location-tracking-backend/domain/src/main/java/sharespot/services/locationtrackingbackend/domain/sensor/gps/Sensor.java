package sharespot.services.locationtrackingbackend.domain.sensor.gps;

import java.util.UUID;

public record Sensor(String name, UUID id) {

    public boolean has(String in) {
        return name.equalsIgnoreCase(in.trim()) || id.toString().equalsIgnoreCase(in.trim());
    }
}
