import {DeviceRecordDTO, RecordTypeDTO, SensorDataRecordLabelDTO} from "../dtos/RecordsDTO";
import {DeviceRecord} from "../model/DeviceRecord";
import {RecordEntry} from "../model/RecordEntry";
import {RecordType} from "../model/RecordType";
import {SensorDataRecordLabel} from "../model/SensorDataRecordLabel";

export class RecordMapper {

  static dtoToModel(dto: DeviceRecordDTO): DeviceRecord {
    let entries = dto.entries.map(e => {
      if (e.type == RecordTypeDTO.BASIC) {
        return new RecordEntry(e.label, e.content, RecordType.BASIC)
      } else {
        if ((e.label as SensorDataRecordLabelDTO) === SensorDataRecordLabelDTO.GPS_LONGITUDE) {
          return new RecordEntry(SensorDataRecordLabel.GPS_LONGITUDE, e.content, RecordType.SENSOR_DATA)
        } else {
          return new RecordEntry(SensorDataRecordLabel.GPS_LATITUDE, e.content, RecordType.SENSOR_DATA)
        }
      }
    })
    return new DeviceRecord(dto.deviceId, entries);
  }

  static modelToDto(model: DeviceRecord): DeviceRecordDTO {
    let entries = model.entries.map(e => {
      if (e.type == RecordType.BASIC) {
        return {label: e.label, content: e.content, type: RecordTypeDTO.BASIC}
      } else {
        if ((e.label as SensorDataRecordLabel) === SensorDataRecordLabel.GPS_LONGITUDE) {
          return {label: SensorDataRecordLabelDTO.GPS_LONGITUDE, content: e.content, type: RecordTypeDTO.SENSOR_DATA}
        } else {
          return {label: SensorDataRecordLabelDTO.GPS_LATITUDE, content: e.content, type: RecordTypeDTO.SENSOR_DATA}
        }
      }
    })
    return {deviceId: model.deviceId, entries: entries}
  }
}
