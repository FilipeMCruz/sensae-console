package pt.sensae.services.device.commander.domain;

import pt.sensae.services.device.commander.domain.commands.DeviceCommandReceived;
import pt.sensae.services.device.commander.domain.device.DeviceId;

import java.util.List;

public interface UnHandledDeviceCommandRepository {

    void insert(DeviceCommandReceived data, DeviceId id);

    List<DeviceCommandReceived> retrieve(DeviceId id);
}
