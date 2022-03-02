package sharespot.services.devicerecordsbackend.application;

import org.springframework.stereotype.Service;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import sharespot.services.devicerecordsbackend.domain.model.DeviceId;

import javax.annotation.PostConstruct;

@Service
public class RecordEventHandlerService {

    private FluxSink<DeviceDTO> dataStream;

    private ConnectableFlux<DeviceDTO> dataPublisher;
    
    private final RecordEventMapper mapper;

    public RecordEventHandlerService(RecordEventMapper mapper) {
        this.mapper = mapper;
    }

    @PostConstruct
    public void init() {
        Flux<DeviceDTO> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<DeviceDTO> getSinglePublisher() {
        return dataPublisher;
    }

    public void publishUpdate(DeviceId data) {
        var outDTO = mapper.domainToDto(data);
        dataStream.next(outDTO);
    }
}
