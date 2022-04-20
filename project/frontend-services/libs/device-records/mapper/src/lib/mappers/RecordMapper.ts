import {DeviceRecordDTO, RecordTypeDTO, SensorDataRecordLabelDTO,} from '@frontend-services/device-records/dto';
import {
  Device,
  DeviceRecord,
  RecordEntry,
  RecordEntryType,
  SensorDataRecordLabel,
  SubDevice
} from '@frontend-services/device-records/model';

export class RecordMapper {
  static dtoToModel(dto: DeviceRecordDTO): DeviceRecord {
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

    const device = new Device(dto.device.id, dto.device.name);
    return new DeviceRecord(device, entries, subDevices);
  }

  static modelToDto(model: DeviceRecord): DeviceRecordDTO {
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
    })
    return {
      device: {id: model.device.id, name: model.device.name},
      entries: entries,
      subDevices: subDevices
    };
  }
}
