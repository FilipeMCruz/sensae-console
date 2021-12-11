package sharespot.services.devicerecordsbackend.application;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceId;
import sharespot.services.devicerecordsbackend.domain.model.records.DeviceRecords;

@Service
public class RecordEventHandlerService {

    private final Sinks.Many<DeviceRecordEventDTO> sink;
    private final RecordEventMapper mapper;

    public RecordEventHandlerService(RecordEventMapper mapper) {
        this.mapper = mapper;
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
    }

    public Flux<DeviceRecordEventDTO> getSinglePublisher() {
        return sink.asFlux();
    }

    public void publishIndex(DeviceRecords data) {
        var outDTO = mapper.domainToDto(data);
        var result = sink.tryEmitNext(outDTO);
        if (result.isFailure()) {
            //TODO: logs
        }
    }

    public void publishErased(DeviceId data) {
        var outDTO = mapper.domainToDto(data);
        var result = sink.tryEmitNext(outDTO);
        if (result.isFailure()) {
            //TODO: logs
        }
    }

}
