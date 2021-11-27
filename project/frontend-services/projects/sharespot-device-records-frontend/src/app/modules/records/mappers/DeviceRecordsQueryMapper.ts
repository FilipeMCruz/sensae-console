import {DeviceRecordQuery} from "../dtos/RecordsDTO";
import {DeviceRecord} from "../model/DeviceRecord";
import {RecordMapper} from "./RecordMapper";

export class DeviceRecordsQueryMapper {
  static dtoToModel(query: DeviceRecordQuery): Array<DeviceRecord> {
    return query.deviceRecords.map(e => RecordMapper.dtoToModel(e));
  }
}
