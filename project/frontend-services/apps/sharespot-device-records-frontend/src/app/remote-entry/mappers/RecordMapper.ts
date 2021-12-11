import {DeviceRecordDTO, RecordTypeDTO, SensorDataRecordLabelDTO} from "../dtos/RecordsDTO";
import {DeviceRecord} from "../model/DeviceRecord";
import {RecordEntry} from "../model/RecordEntry";
import {RecordEntryType} from "../model/RecordEntryType";
import {SensorDataRecordLabel} from "../model/SensorDataRecordLabel";
import {Device} from "../model/Device";

export class RecordMapper {

  static dtoToModel(dto: DeviceRecordDTO): DeviceRecord {
    const entries = dto.entries.map(e => {
      if (e.type == RecordTypeDTO.BASIC) {
        return new RecordEntry(e.label, e.content, RecordEntryType.BASIC)
      } else {
        if ((e.label as SensorDataRecordLabelDTO) === SensorDataRecordLabelDTO.GPS_LONGITUDE) {
          return new RecordEntry(SensorDataRecordLabel.GPS_LONGITUDE, e.content, RecordEntryType.SENSOR_DATA)
        } else {
          return new RecordEntry(SensorDataRecordLabel.GPS_LATITUDE, e.content, RecordEntryType.SENSOR_DATA)
        }
      }
    });
    const device = new Device(dto.device.id, dto.device.name);
    return new DeviceRecord(device, entries);
  }

  static modelToDto(model: DeviceRecord): DeviceRecordDTO {
    const entries = model.entries.map(e => {
      if (e.type == RecordEntryType.BASIC) {
        return {label: e.label, content: e.content, type: RecordTypeDTO.BASIC}
      } else {
        if ((e.label as SensorDataRecordLabel) === SensorDataRecordLabel.GPS_LONGITUDE) {
          return {label: SensorDataRecordLabelDTO.GPS_LONGITUDE, content: e.content, type: RecordTypeDTO.SENSOR_DATA}
        } else {
          return {label: SensorDataRecordLabelDTO.GPS_LATITUDE, content: e.content, type: RecordTypeDTO.SENSOR_DATA}
        }
      }
    })
    return {device: {id: model.device.id, name: model.device.name}, entries: entries}
  }
}
