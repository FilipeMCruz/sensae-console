package sharespot.services.data.decoder.master.application;

import org.springframework.stereotype.Service;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import sharespot.services.data.decoder.master.domain.DataDecoder;
import sharespot.services.data.decoder.master.domain.SensorTypeId;

import javax.annotation.PostConstruct;

@Service
public class DataDecoderHandlerService {

    private FluxSink<DataDecoderNotificationDTO> dataStream;

    private ConnectableFlux<DataDecoderNotificationDTO> dataPublisher;

    private final DataDecoderEventMapper mapper;

    public DataDecoderHandlerService(DataDecoderEventMapper mapper) {
        this.mapper = mapper;
    }

    @PostConstruct
    public void init() {
        Flux<DataDecoderNotificationDTO> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<DataDecoderNotificationDTO> getSinglePublisher() {
        return dataPublisher;
    }

    public void publishUpdate(DataDecoder domain) {
        var outDTO = mapper.domainToUpdatedDto(domain);
        dataStream.next(outDTO);
    }

    public void publishDelete(SensorTypeId domain) {
        var outDTO = mapper.domainToDeletedDto(domain);
        dataStream.next(outDTO);
    }
}
