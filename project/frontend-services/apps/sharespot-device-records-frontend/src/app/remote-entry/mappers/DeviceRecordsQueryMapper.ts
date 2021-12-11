import {DeviceRecordQuery} from "../dtos/RecordsDTO";
import {DeviceRecord} from "../model/DeviceRecord";
import {RecordMapper} from "./RecordMapper";

export class DeviceRecordsQueryMapper {
  static dtoToModel(dto: DeviceRecordQuery): Array<DeviceRecord> {
    return dto.deviceRecords.map(e => RecordMapper.dtoToModel(e));
  }
}
