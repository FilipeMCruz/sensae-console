package sharespot.services.identitymanagementbackend.application.service.device;

import org.springframework.stereotype.Service;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceId;

import javax.annotation.PostConstruct;

@Service
public class DeviceUpdateHandlerService {

    private FluxSink<DeviceId> dataStream;

    private ConnectableFlux<DeviceId> dataPublisher;

    @PostConstruct
    public void init() {
        Flux<DeviceId> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<DeviceId> getSinglePublisher() {
        return dataPublisher;
    }

    public void publishUpdate(DeviceId data) {
        dataStream.next(data);
    }
}
