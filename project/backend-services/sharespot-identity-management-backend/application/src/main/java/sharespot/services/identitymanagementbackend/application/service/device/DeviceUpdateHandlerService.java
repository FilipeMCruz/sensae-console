package sharespot.services.identitymanagementbackend.application.service.device;

import org.springframework.stereotype.Service;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import sharespot.services.identitymanagementbackend.application.model.device.DeviceDTO;
import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceId;

import javax.annotation.PostConstruct;

@Service
public class DeviceUpdateHandlerService {

    private FluxSink<DeviceDTO> dataStream;

    private ConnectableFlux<DeviceDTO> dataPublisher;

    private final DeviceEventMapper mapper;

    public DeviceUpdateHandlerService(DeviceEventMapper mapper) {
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
