import {DeviceRecordRegister} from "../dtos/RecordsDTO";
import {RecordMapper} from "./RecordMapper";
import {DeviceRecord} from "../model/DeviceRecord";

export class DeviceRecordRegisterMapper {
  static dtoToModel(dto: DeviceRecordRegister): DeviceRecord {
    return RecordMapper.dtoToModel(dto.index)
  }
}
