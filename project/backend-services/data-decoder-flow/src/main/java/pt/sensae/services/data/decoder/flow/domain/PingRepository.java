package pt.sensae.services.data.decoder.flow.domain;


import java.util.stream.Stream;

public interface PingRepository {
    
    void store(SensorTypeId ping);
    
    Stream<SensorTypeId> retrieveAll();
}
