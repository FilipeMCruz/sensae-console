package pt.sensae.services.data.processor.master.backend.application;

import org.springframework.stereotype.Service;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import pt.sensae.services.data.processor.master.backend.domain.DataTransformation;
import pt.sensae.services.data.processor.master.backend.domain.SensorTypeId;

import javax.annotation.PostConstruct;

@Service
public class DataTransformationEventHandlerService {

    private FluxSink<DataTransformationNotificationDTO> dataStream;

    private ConnectableFlux<DataTransformationNotificationDTO> dataPublisher;

    private final DataTransformationEventMapper mapper;

    public DataTransformationEventHandlerService(DataTransformationEventMapper mapper) {
        this.mapper = mapper;
    }

    @PostConstruct
    public void init() {
        Flux<DataTransformationNotificationDTO> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<DataTransformationNotificationDTO> getSinglePublisher() {
        return dataPublisher;
    }

    public void publishUpdate(DataTransformation domain) {
        var outDTO = mapper.domainToUpdatedDto(domain);
        dataStream.next(outDTO);
    }

    public void publishDelete(SensorTypeId domain) {
        var outDTO = mapper.domainToDeletedDto(domain);
        dataStream.next(outDTO);
    }
}
