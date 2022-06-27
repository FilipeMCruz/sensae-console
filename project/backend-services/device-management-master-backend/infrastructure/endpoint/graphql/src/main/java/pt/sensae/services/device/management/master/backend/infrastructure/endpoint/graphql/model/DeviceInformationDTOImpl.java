package pt.sensae.services.device.management.master.backend.infrastructure.endpoint.graphql.model;

import pt.sensae.services.device.management.master.backend.application.DeviceInformationDTO;

import java.util.Set;

public class DeviceInformationDTOImpl implements DeviceInformationDTO {

    public DeviceDTOImpl device;

    public Set<RecordEntryDTOImpl> records;

    public Set<DeviceStaticDataDTOImpl> staticData;

    public Set<SubDeviceDTOImpl> subDevices;

    public Set<DeviceCommandDTOImpl> commands;
    
    public String lastTimeSeen;

}
