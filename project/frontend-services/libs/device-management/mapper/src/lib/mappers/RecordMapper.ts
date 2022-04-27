import {DeviceRecordDTO, RecordTypeDTO, SensorDataRecordLabelDTO,} from '@frontend-services/device-management/dto';
import {
  Device, DeviceCommand,
  DeviceInformation,
  RecordEntry,
  RecordEntryType,
  SensorDataRecordLabel,
  SubDevice
} from '@frontend-services/device-management/model';

export class RecordMapper {
  static dtoToModel(dto: DeviceRecordDTO): DeviceInformation {
    const entries = dto.entries.map((e) => {
      if (e.type == RecordTypeDTO.BASIC) {
        return new RecordEntry(e.label, e.content, RecordEntryType.BASIC);
      } else {
        if (
          (e.label as SensorDataRecordLabelDTO) ===
          SensorDataRecordLabelDTO.GPS_LONGITUDE
        ) {
          return new RecordEntry(
            SensorDataRecordLabel.GPS_LONGITUDE,
            e.content,
            RecordEntryType.SENSOR_DATA
          );
        } else {
          return new RecordEntry(
            SensorDataRecordLabel.GPS_LATITUDE,
            e.content,
            RecordEntryType.SENSOR_DATA
          );
        }
      }
    });
    const subDevices = dto.subDevices.map(deviceRef => new SubDevice(deviceRef.ref, deviceRef.id));

    const commands = dto.commands.map(com => new DeviceCommand(com.id, com.name, com.payload, com.ref, com.port));

    const device = new Device(dto.device.id, dto.device.name, dto.device.downlink);
    return new DeviceInformation(device, entries, subDevices, commands);
  }

  static modelToDto(model: DeviceInformation): DeviceRecordDTO {
    const entries = model.entries.map((e) => {
      if (e.type == RecordEntryType.BASIC) {
        return {
          label: e.label,
          content: e.content,
          type: RecordTypeDTO.BASIC,
        };
      } else {
        if (
          (e.label as SensorDataRecordLabel) ===
          SensorDataRecordLabel.GPS_LONGITUDE
        ) {
          return {
            label: SensorDataRecordLabelDTO.GPS_LONGITUDE,
            content: e.content,
            type: RecordTypeDTO.SENSOR_DATA,
          };
        } else {
          return {
            label: SensorDataRecordLabelDTO.GPS_LATITUDE,
            content: e.content,
            type: RecordTypeDTO.SENSOR_DATA,
          };
        }
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
      entries: entries,
      subDevices: subDevices,
      commands: commands
    };
  }
}
