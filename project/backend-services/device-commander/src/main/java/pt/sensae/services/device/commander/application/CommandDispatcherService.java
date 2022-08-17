package pt.sensae.services.device.commander.application;

import pt.sensae.services.device.commander.domain.commands.CommandEntry;
import pt.sensae.services.device.commander.domain.commands.DeviceCommandReceived;
import pt.sensae.services.device.commander.domain.device.DeviceDownlink;
import pt.sensae.services.device.commander.infrastructure.persistence.memory.DeviceInformationCache;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import java.net.URI;

@ApplicationScoped
public class CommandDispatcherService {

    @Inject
    DeviceInformationCache cache;

    Client client = ClientBuilder.newClient();

    public void tryToSend(DeviceCommandReceived command) {
        cache.findById(command.deviceId())
                .ifPresent(device -> device.commands()
                        .entries()
                        .stream()
                        .filter(c -> c.id().equals(command.commandId()))
                        .findFirst()
                        .ifPresent(com -> send(com, device.device().downlink())));
    }

    private void send(CommandEntry entry, DeviceDownlink downlink) {
        client.target(URI.create(downlink.value()))
                .request()
                .async()
                .post(Entity.entity(entry.toCommandRequest().toString(), "application/json"), String.class);
    }

    @PreDestroy
    void destroy() {
        client.close();
    }
}
