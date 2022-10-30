package pt.sensae.services.data.processor.flow.domain;


import java.util.stream.Stream;

public interface PingRepository {
    
    void store(SensorTypeId ping);
    
    Stream<SensorTypeId> retrieveAll();
}
