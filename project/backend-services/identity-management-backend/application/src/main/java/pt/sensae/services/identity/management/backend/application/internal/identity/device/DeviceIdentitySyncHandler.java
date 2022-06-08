package pt.sensae.services.identity.management.backend.application.internal.identity.device;


import java.util.stream.Stream;

public interface DeviceIdentitySyncHandler {

    void publishState(Stream<DeviceIdentityDTO> devices);

}
