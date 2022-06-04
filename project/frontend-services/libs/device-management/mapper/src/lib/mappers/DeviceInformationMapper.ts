import {DeviceInformationDTO, SensorDataRecordLabelDTO,} from '@frontend-services/device-management/dto';
import {
  Device, DeviceCommand,
  DeviceInformation,
  RecordEntry,
  SensorDataRecordLabel,
  SubDevice
} from '@frontend-services/device-management/model';
import {StaticDataEntry} from "@frontend-services/device-management/model";

export class DeviceInformationMapper {
  static dtoToModel(dto: DeviceInformationDTO): DeviceInformation {
    const records = dto.records.map((e) => {
      return new RecordEntry(e.label, e.content);
    });
    const staticData = dto.staticData.map((e) => {
      if (e.label === SensorDataRecordLabelDTO.GPS_LONGITUDE) {
        return new StaticDataEntry(SensorDataRecordLabel.GPS_LONGITUDE, e.content);
      } else {
        return new StaticDataEntry(SensorDataRecordLabel.GPS_LATITUDE, e.content);
      }
    });
    const subDevices = dto.subDevices.map(deviceRef => new SubDevice(deviceRef.ref, deviceRef.id));

    const commands = dto.commands.map(com => new DeviceCommand(com.id, com.name, com.payload, com.ref, com.port));

    const device = new Device(dto.device.id, dto.device.name, dto.device.downlink);
    return new DeviceInformation(device, records, staticData, subDevices, commands);
  }

  static modelToDto(model: DeviceInformation): DeviceInformationDTO {
    const records = model.records.map((e) => {
      return {
        label: e.label,
        content: e.content,
      };
    });
    const staticData = model.staticData.map((e) => {
      if (e.label === SensorDataRecordLabel.GPS_LONGITUDE) {
        return {
          label: SensorDataRecordLabelDTO.GPS_LONGITUDE,
          content: e.content,
        };
      } else {
        return {
          label: SensorDataRecordLabelDTO.GPS_LATITUDE,
          content: e.content,
        };
      }
    });

    const subDevices = model.subDevices.map(sub => {
      return {
        id: sub.id,
        ref: sub.reference
      }
    });

    const commands = model.commands.map(com => {
      return {
        id: com.id,
        name: com.name,
        payload: com.payload,
        ref: com.ref,
        port: com.port
      }
    });

    return {
      device: {id: model.device.id, name: model.device.name, downlink: model.device.downlink},
      records: records,
      subDevices: subDevices,
      staticData: staticData,
      commands: commands
    };
  }
}
