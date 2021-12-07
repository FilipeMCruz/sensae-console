import {DeviceRecordDelete} from "../dtos/RecordsDTO";
import {RecordMapper} from "./RecordMapper";
import {DeviceRecord} from "../model/DeviceRecord";

export class DeviceRecordDeleteMapper {

  static dtoToModel(dto: DeviceRecordDelete): Array<DeviceRecord> {
    return dto.delete.map(e => RecordMapper.dtoToModel(e));
  }
}
