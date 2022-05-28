package sharespot.services.identitymanagementbackend.application.internal.device;

import org.springframework.stereotype.Service;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import sharespot.services.identitymanagementbackend.domain.identity.device.DeviceWithAllOwnerDomains;

import javax.annotation.PostConstruct;

@Service
public class DeviceInformationEventHandlerService {

    private FluxSink<DeviceNotificationDTO> dataStream;

    private ConnectableFlux<DeviceNotificationDTO> dataPublisher;

    private final NotificationEventMapper mapper;

    public DeviceInformationEventHandlerService(NotificationEventMapper mapper) {
        this.mapper = mapper;
    }

    @PostConstruct
    public void init() {
        Flux<DeviceNotificationDTO> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<DeviceNotificationDTO> getSinglePublisher() {
        return dataPublisher;
    }

    public void publishUpdate(DeviceWithAllOwnerDomains domain) {
        var outDTO = mapper.domainToUpdatedDto(domain);
        dataStream.next(outDTO);
    }
}
