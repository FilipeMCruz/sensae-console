package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.graphql.model;

import pt.sensae.services.device.management.master.backend.application.DeviceInformationDTO;

import java.util.Set;

public class DeviceRecordDTOImpl implements DeviceInformationDTO {
    public DeviceDTOImpl device;
    public Set<RecordEntryDTOImpl> entries;

    public Set<SubDeviceDTOImpl> subDevices;

    public Set<DeviceCommandDTOImpl> commands;

}
