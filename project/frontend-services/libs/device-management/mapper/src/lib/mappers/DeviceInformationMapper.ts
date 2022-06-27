import {
  DeviceInformationDTO,
  DeviceInformationResultDTO,
  SensorDataRecordLabelDTO,
} from '@frontend-services/device-management/dto';
import {
  Device,
  DeviceCommand,
  DeviceInformation,
  RecordEntry,
  SensorDataRecordLabel,
  StaticDataEntry,
  SubDevice
} from '@frontend-services/device-management/model';

export class DeviceInformationMapper {
  static dtoToModel(dto: DeviceInformationResultDTO): DeviceInformation {
    const records = dto.records.map((e) => new RecordEntry(e.label, e.content));

    const staticData = dto.staticData.map((e) => new StaticDataEntry(DeviceInformationMapper.labelDtoToModel(e.label), e.content));

    const subDevices = dto.subDevices.map(deviceRef => new SubDevice(deviceRef.ref, deviceRef.id));

    const commands = dto.commands.map(com => new DeviceCommand(com.id, com.name, com.payload, com.ref, com.port));

    const device = new Device(dto.device.id, dto.device.name, dto.device.downlink);

    const d = new Date(0); // The 0 there is the key, which sets the date to the epoch
    d.setUTCMilliseconds(dto.lastTimeSeen);
    return new DeviceInformation(device, records, staticData, subDevices, commands, d);
  }

  static modelToDto(model: DeviceInformation): DeviceInformationDTO {
    const records = model.records.map((e) => ({
      label: e.label,
      content: e.content,
    }));
    const staticData = model.staticData.map((e) => ({
      label: DeviceInformationMapper.labelModelToDto(e.label),
      content: e.content,
    }));

    const subDevices = model.subDevices.map(sub => ({
      id: sub.id,
      ref: sub.reference
    }));

    const commands = model.commands.map(com => ({
      id: com.id,
      name: com.name,
      payload: com.payload,
      ref: com.ref,
      port: com.port
    }));

    return {
      device: {id: model.device.id, name: model.device.name, downlink: model.device.downlink},
      records: records,
      subDevices: subDevices,
      staticData: staticData,
      commands: commands
    };
  }

  static labelModelToDto(model: SensorDataRecordLabel): SensorDataRecordLabelDTO {
    switch (model) {
      case SensorDataRecordLabel.BATTERY_MAX_VOLTS:
        return SensorDataRecordLabelDTO.BATTERY_MAX_VOLTS
      case SensorDataRecordLabel.BATTERY_MIN_VOLTS:
        return SensorDataRecordLabelDTO.BATTERY_MIN_VOLTS
      case SensorDataRecordLabel.MAX_DISTANCE:
        return SensorDataRecordLabelDTO.MAX_DISTANCE
      case SensorDataRecordLabel.MIN_DISTANCE:
        return SensorDataRecordLabelDTO.MIN_DISTANCE
      case SensorDataRecordLabel.GPS_ALTITUDE:
        return SensorDataRecordLabelDTO.GPS_ALTITUDE
      case SensorDataRecordLabel.GPS_LATITUDE:
        return SensorDataRecordLabelDTO.GPS_LATITUDE
      case SensorDataRecordLabel.GPS_LONGITUDE:
        return SensorDataRecordLabelDTO.GPS_LONGITUDE
      case SensorDataRecordLabel.ERROR:
        return SensorDataRecordLabelDTO.ERROR;
    }
  }

  static labelDtoToModel(model: SensorDataRecordLabelDTO): SensorDataRecordLabel {
    switch (model) {
      case SensorDataRecordLabelDTO.BATTERY_MAX_VOLTS:
        return SensorDataRecordLabel.BATTERY_MAX_VOLTS
      case SensorDataRecordLabelDTO.BATTERY_MIN_VOLTS:
        return SensorDataRecordLabel.BATTERY_MIN_VOLTS
      case SensorDataRecordLabelDTO.MAX_DISTANCE:
        return SensorDataRecordLabel.MAX_DISTANCE
      case SensorDataRecordLabelDTO.MIN_DISTANCE:
        return SensorDataRecordLabel.MIN_DISTANCE
      case SensorDataRecordLabelDTO.GPS_ALTITUDE:
        return SensorDataRecordLabel.GPS_ALTITUDE
      case SensorDataRecordLabelDTO.GPS_LATITUDE:
        return SensorDataRecordLabel.GPS_LATITUDE
      case SensorDataRecordLabelDTO.GPS_LONGITUDE:
        return SensorDataRecordLabel.GPS_LONGITUDE
      case SensorDataRecordLabelDTO.ERROR:
        return SensorDataRecordLabel.ERROR;
    }
  }
}
