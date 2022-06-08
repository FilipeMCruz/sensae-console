package pt.sensae.services.device.management.master.backend.application;


import java.util.stream.Stream;

public interface DeviceInformationSyncHandler {

    void publishState(Stream<DeviceNotificationDTO> devices);

}
