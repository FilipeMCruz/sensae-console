import {DeviceRecordsInput} from "../dtos/RecordsDTO";
import {DeviceRecord} from "../model/DeviceRecord";
import {RecordMapper} from "./RecordMapper";

export class DeviceRecordRegisterMapper {
  static modelToDto(model: DeviceRecord): DeviceRecordsInput {
    return {index: RecordMapper.modelToDto(model)};
  }

  static dtoToModel(dto: DeviceRecordsInput): DeviceRecord {
    return RecordMapper.dtoToModel(dto.index);
  }
}
