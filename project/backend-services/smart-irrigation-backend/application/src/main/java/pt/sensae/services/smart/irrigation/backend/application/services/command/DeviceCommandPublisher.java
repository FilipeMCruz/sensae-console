package pt.sensae.services.smart.irrigation.backend.application.services.command;

import org.springframework.stereotype.Service;
import pt.sensae.services.smart.irrigation.backend.application.mapper.command.DeviceCommandMapper;
import pt.sensae.services.smart.irrigation.backend.application.model.command.DeviceCommandDTO;
import pt.sensae.services.smart.irrigation.backend.domain.model.business.device.command.DeviceCommand;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.annotation.PostConstruct;

@Service
public class DeviceCommandPublisher {
    private FluxSink<DeviceCommandDTO> dataStream;

    private ConnectableFlux<DeviceCommandDTO> dataPublisher;

    private final DeviceCommandMapper mapper;

    public DeviceCommandPublisher(DeviceCommandMapper mapper) {
        this.mapper = mapper;
    }

    @PostConstruct
    public void init() {
        Flux<DeviceCommandDTO> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public void publish(DeviceCommand data) {
        dataStream.next(mapper.toDTO(data));
    }

    public Flux<DeviceCommandDTO> getPublisher() {
        return dataPublisher;
    }
}
