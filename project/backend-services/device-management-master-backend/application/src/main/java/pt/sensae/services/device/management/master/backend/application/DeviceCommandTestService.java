package pt.sensae.services.device.management.master.backend.application;

import pt.sensae.services.device.management.master.backend.application.command.DeviceCommandDTO;

public interface DeviceCommandTestService {
    
    void send(DeviceCommandDTO command);
}
