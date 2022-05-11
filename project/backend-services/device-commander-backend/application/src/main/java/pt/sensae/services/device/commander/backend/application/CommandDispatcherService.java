package pt.sensae.services.device.commander.backend.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pt.sensae.services.device.commander.backend.domain.model.commands.CommandEntry;
import pt.sensae.services.device.commander.backend.domain.model.commands.DeviceCommandReceived;
import pt.sensae.services.device.commander.backend.domain.model.device.DeviceDownlink;
import pt.sensae.services.device.commander.backend.domainservices.DeviceRecordCache;

@Service
public class CommandDispatcherService {

    private final DeviceRecordCache cache;

    private final RestTemplate template;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandDispatcherService.class);

    public CommandDispatcherService(DeviceRecordCache cache, RestTemplate template) {
        this.cache = cache;
        this.template = template;
    }

    public void tryToSend(DeviceCommandReceived command) {
        cache.findByDeviceId(command.deviceId())
                .ifPresent(device -> device.commands()
                        .entries()
                        .stream()
                        .filter(c -> c.id().equals(command.commandId()))
                        .findFirst()
                        .ifPresent(com -> send(com, device.device().downlink())));
    }

    private void send(CommandEntry entry, DeviceDownlink downlink) {
        LOGGER.info(entry.toCommandRequest().toString());
        template.postForLocation(downlink.value(), new HttpEntity<>(entry.toCommandRequest()));
    }
}
