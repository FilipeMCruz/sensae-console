package sharespot.services.identitymanagementbackend.application.internal.device;


import java.util.stream.Stream;

public interface DeviceIdentitySyncHandler {

    void publishState(Stream<DeviceIdentityDTO> devices);

}
