package sharespot.services.identitymanagementbackend.application.internal.device;

import org.springframework.stereotype.Service;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceWithAllOwnerDomains;

import javax.annotation.PostConstruct;

@Service
public class DeviceInformationEventHandlerService {

    private FluxSink<DeviceIdentityDTO> dataStream;

    private ConnectableFlux<DeviceIdentityDTO> dataPublisher;

    private final DeviceIdentityMapper mapper;

    public DeviceInformationEventHandlerService(DeviceIdentityMapper mapper) {
        this.mapper = mapper;
    }

    @PostConstruct
    public void init() {
        Flux<DeviceIdentityDTO> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<DeviceIdentityDTO> getSinglePublisher() {
        return dataPublisher;
    }

    public void publishUpdate(DeviceWithAllOwnerDomains domain) {
        var outDTO = mapper.domainToDto(domain);
        dataStream.next(outDTO);
    }
}
