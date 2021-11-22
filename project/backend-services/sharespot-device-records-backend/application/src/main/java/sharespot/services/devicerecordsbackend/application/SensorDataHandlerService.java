package sharespot.services.devicerecordsbackend.application;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class SensorDataHandlerService {

    private final Sinks.Many<ProcessedSensorDataWithRecordDTO> sink;
    private final RecordAppenderService appender;

    public SensorDataHandlerService(RecordAppenderService appender) {
        this.appender = appender;
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
    }

    public Flux<ProcessedSensorDataWithRecordDTO> getSinglePublisher() {
        return sink.asFlux();
    }

    public void publish(ProcessedSensorDataDTO data) {
        var outDTO = appender.tryToAppend(data);
        var result = sink.tryEmitNext(outDTO);
        if (result.isFailure()) {
            //TODO: logs
        }
    }

}
