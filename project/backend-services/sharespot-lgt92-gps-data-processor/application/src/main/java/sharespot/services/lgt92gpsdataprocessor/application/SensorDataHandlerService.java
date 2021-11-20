package sharespot.services.lgt92gpsdataprocessor.application;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class SensorDataHandlerService {

    private final Sinks.Many<OutSensorDataDTO> sink;
    private final SensorDataMapper mapper;

    public SensorDataHandlerService(SensorDataMapper mapper) {
        this.mapper = mapper;
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
    }

    public Flux<OutSensorDataDTO> getSinglePublisher() {
        return sink.asFlux();
    }

    public void publish(InSensorDataDTO data) {
        var outDTO = mapper.inToOut(data);
        var result = sink.tryEmitNext(outDTO);
        if (result.isFailure()) {
            //TODO: logs
        }
    }

}
