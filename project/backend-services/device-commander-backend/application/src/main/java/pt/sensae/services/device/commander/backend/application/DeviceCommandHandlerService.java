package pt.sensae.services.device.commander.backend.application;

import org.springframework.stereotype.Service;

@Service
public class DeviceCommandHandlerService {
    private final CommandDispatcherService dispatcher;

    private final DeviceCommandMapper mapper;

    public DeviceCommandHandlerService(CommandDispatcherService dispatcher, DeviceCommandMapper mapper) {
        this.dispatcher = dispatcher;
        this.mapper = mapper;
    }

    public void publish(DeviceCommandDTO command) {
        dispatcher.tryToSend(mapper.toModel(command));
    }
}
