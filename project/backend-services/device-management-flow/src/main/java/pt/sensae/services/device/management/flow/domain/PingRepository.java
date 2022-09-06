package pt.sensae.services.device.management.flow.domain;

import pt.sensae.services.device.management.flow.domain.device.Device;

import java.util.stream.Stream;

public interface PingRepository {
    
    void store(Device ping);
    
    Stream<Device> retrieveAll();
}
