package pt.sensae.services.device.ownership.flow.domain;


import java.util.stream.Stream;

public interface PingRepository {

    void store(DeviceId ping);

    Stream<DeviceId> retrieveAll();
}
