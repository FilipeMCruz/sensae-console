package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.graphql.model;

import pt.sensae.services.device.management.master.backend.application.DeviceRecordDTO;

import java.util.Set;

public class DeviceRecordDTOImpl implements DeviceRecordDTO {
    public DeviceDTOImpl device;
    public Set<RecordEntryDTOImpl> entries;

    public Set<SubDeviceDTOImpl> subDevices;

    public Set<DeviceCommandDTOImpl> commands;

}
